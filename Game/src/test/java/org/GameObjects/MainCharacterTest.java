package org.GameObjects;

import org.Display.*;
import org.GameObjects.*;
import org.Logic.*;
import org.Input.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainCharacterTest {
    Screen screen = new Screen();
    TileManager gameTiles = new TileManager(screen);
    KeyHandler handler = new KeyHandler();
    ImmovableObjectDisplay displayer = new ImmovableObjectDisplay(screen, gameTiles);

    CollisionDetector detector = new CollisionDetector(screen, gameTiles);

    @Test
    void touchObject() {
        MainCharacter player = new MainCharacter(screen, handler, detector, displayer, gameTiles);
        displayer.displayObjects(17);

        // test touching a carrot
        int score_before_touched = screen.getScore();
        player.touchObject(0);
        assertEquals(score_before_touched+1, screen.getScore()); // test score updated
        assertNull(screen.getObjects()[0]); // test carrot removed from screen

        // test touching a mystical ocean fruit
        score_before_touched = screen.getScore();
        player.touchObject(10);
        assertEquals(score_before_touched+3, screen.getScore()); // test score updated
        assertNull(screen.getObjects()[10]); // test fruit removed from screen

        // test touching lava
        player.touchObject(1);
        player.touchObject(2);
        player.touchObject(3);
        screen.updateScore(3);
        score_before_touched = screen.getScore();
        int num_carrots_before_touched = 0;
        for(int i = 0; i < 10; i++) {
            if(screen.getObjects()[i] != null) {
                num_carrots_before_touched++;
            }
        }

        player.touchObject(15);
        assertEquals(score_before_touched-3, screen.getScore()); // test score updated

        // test 3 carrots added to screen
        int num_carrots_after_touched = 0;
        for(int i = 0; i < 10; i++) {
            if(screen.getObjects()[i] != null) {
                num_carrots_after_touched++;
            }
        }
        assertEquals(num_carrots_before_touched+3, num_carrots_after_touched);
    }

    @Test
    void exitCave() {
        MainCharacter player = new MainCharacter(screen, handler, detector, displayer, gameTiles);

        screen.startGameThread();
        player.exitCave(3);
        assertNotNull(screen.getWinningMenu());
    }
}