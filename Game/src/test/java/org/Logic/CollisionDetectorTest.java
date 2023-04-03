package org.Logic;

import org.Display.ImmovableObjectDisplay;
import org.Display.Screen;
import org.GameObjects.Enemy;
import org.GameObjects.MainCharacter;
import org.GameObjects.TileManager;
import org.Input.KeyHandler;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CollisionDetectorTest {
    Screen screen = new Screen();
    TileManager gameTiles = new TileManager(screen);
    KeyHandler handler = new KeyHandler();
    ImmovableObjectDisplay displayer = new ImmovableObjectDisplay(screen, gameTiles);
    CollisionDetector detector = new CollisionDetector(screen, gameTiles);
    int tileSize = screen.getTileSize();

    @Test
    public void testDetectTile() {
        MainCharacter player = new MainCharacter(screen, handler, detector, displayer, gameTiles);

        // test  touching background tile
        int startingPos = detector.detectTile(player);
        assertEquals(0, startingPos); // at the starting position, the tile type is 0 (cave background)

        // test touching outside barrier
        player.setPosition(2 * tileSize, 30);
        int touchingOutsideBarrier = detector.detectTile(player);
        assertEquals(1, touchingOutsideBarrier); // touching the top outside barrier

        // test touching inside barrier
        player.setPosition(2 * tileSize, 12 * tileSize - 30);
        int touchingInsideBarrier = detector.detectTile(player);
        assertEquals(2, touchingInsideBarrier); // touching one of the inside barriers
    }

    @Test
    public void testDetectImmovableObject() {
        MainCharacter player = new MainCharacter(screen, handler, detector, displayer, gameTiles);

        displayer.displayObjects(17);
        int x = screen.getObjects()[0].getXPos();
        int y = screen.getObjects()[0].getYPos();

        // test touching a carrot
        player.setPosition(x, y);
        int touchingCarrot0 = detector.detectImmovableObject(player);
        assertEquals(0, touchingCarrot0);

        // test touching mystical ocean fruit
        x = screen.getObjects()[10].getXPos();
        y = screen.getObjects()[10].getYPos();
        player.setPosition(x, y);
        int touchingFruit = detector.detectImmovableObject(player);
        assertEquals(10, touchingFruit);

        // test touching lava
        x = screen.getObjects()[14].getXPos(); // lava tile
        y = screen.getObjects()[14].getYPos();
        player.setPosition(x, y);
        int touchingLava = detector.detectImmovableObject(player);
        assertEquals(10, touchingFruit);
    }

    @Test
    public void testEnemyCatchPlayer() {
        MainCharacter player = screen.getPlayer();
        Assert.assertEquals(2, screen.getEnemies().size());
        Enemy enemy = screen.getEnemies().get(0);
        enemy.setStartingValues(player.getXPos(), player.getYPos(), 2, "UNKNOWN");
        Assert.assertTrue(detector.detectEnemyCatchPlayer(player, tileSize));
        enemy.setStartingValues(player.getXPos() + tileSize * 2, player.getYPos() + tileSize * 2, 2, "UNKNOWN");
        Assert.assertFalse(detector.detectEnemyCatchPlayer(player, tileSize));
    }
}