package org.Display;

import org.Display.*;
import org.GameObjects.*;
import org.Logic.*;
import org.Input.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class ImmovableObjectDisplayTest {
    Screen screen = new Screen();
    TileManager gameTiles = new TileManager(screen);
    @Test
    public void testGenerateRandomPosition() {
        ImmovableObjectDisplay displayer = new ImmovableObjectDisplay(screen, gameTiles);
        displayer.displayObjects(17);
        int[][] board = gameTiles.getBoard();

        int[] randomPosition = displayer.generateRandomPosition();
        int x = randomPosition[0]; // generated x position to be tested
        int y = randomPosition[1]; // generated y position to be tested

        // test generated position is an empty cave tile
        assertEquals(0, board[x][y]);

        // test generated position is not already taken

        // don't test last element because that is the newly added random position
        int allExceptNewOne = displayer.getTakenPositions().size() -1;

        for(int i = 0; i < allExceptNewOne; i++) {
            int curX = displayer.getTakenPositions().get(i)[0];
            int curY = displayer.getTakenPositions().get(i)[1];

            boolean bothEqual = (x == curX) && (y == curY);
            assertEquals(false, bothEqual);
        }
    }

    @Test
    public void testDisplayObjects() {
        ImmovableObjectDisplay displayer = new ImmovableObjectDisplay(screen, gameTiles);
        displayer.displayObjects(17);

        // check objects got displayed
        int numObjects = screen.getObjects().length;
        for(int i = 0; i < numObjects; i++) {
            assertNotNull(screen.getObjects()[i]);
        }
    }
}
