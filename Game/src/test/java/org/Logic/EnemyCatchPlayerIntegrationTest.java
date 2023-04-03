package org.Logic;

import org.Display.Screen;
import org.GameObjects.TileManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EnemyCatchPlayerIntegrationTest {
    static Screen screen = new Screen();
    TileManager gameTiles = new TileManager(screen);
    int tileSize = screen.getTileSize();
    CollisionDetector detector = new CollisionDetector(screen, gameTiles);

    @BeforeClass
    public static void before() {
        screen.gameSetup();
        screen.startGameThread();
    }

    @Test
    public void testEnemiesCatchPlayer() {
        Assert.assertTrue(screen.getGameThread().isAlive());
        while (!screen.getGameThread().isInterrupted()) {
            // enemy move to catch player
        }
        Assert.assertTrue(detector.detectEnemyCatchPlayer(screen.getPlayer(), tileSize));
    }

    @AfterClass
    public static void after() {
        System.exit(0);
    }

}
