package org.Logic;

import org.Display.ImmovableObjectDisplay;
import org.Display.Screen;
import org.GameObjects.MainCharacter;
import org.GameObjects.TileManager;
import org.Input.KeyHandler;
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

        int startingPos = detector.detectTile(player);
        assertEquals(0, startingPos); // at the starting position, the tile type is 0 (cave background)

        player.setPosition(2*tileSize, 30);
        int touchingOutsideBarrier = detector.detectTile(player);
        assertEquals(1, touchingOutsideBarrier); // touching the top outside barrier

        player.setPosition(2*tileSize, 12*tileSize - 30);
        int touchingInsideBarrier = detector.detectTile(player);
        assertEquals(2, touchingInsideBarrier); // touching one of the inside barriers
    }
}