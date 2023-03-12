package org.Display;

import org.GameObjects.*;

import java.util.ArrayList;
import java.util.Random;

public class ImmovableObjectDisplay {
    private Screen screen;
    private TileManager tileM;

    public ImmovableObjectDisplay(Screen screen, TileManager tileM) {
        this.screen = screen;
        this.tileM = tileM;
    }

    public void displayObjects() {
        ArrayList<int[]> takenPositions = new ArrayList<int[]>();

        Random rand = new Random();

        int[][] board = tileM.getBoard();
        int numRewards = screen.getRewards().length;

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

        // generate 10 carrots
        for(int i = 0; i < numRewards - 1; i++) {
            // generate random position for the current reward
            int randomXPos = rand.nextInt(screen.getNumColumns());
            int randomYPos = rand.nextInt(screen.getNumRows());

            int[] curPosition = new int[2];
            curPosition[0] = randomXPos;
            curPosition[1] = randomYPos;

            // make sure the position is on an empty tile
            while(board[randomXPos][randomYPos] != 0 && !takenPositions.contains(curPosition)) {
                randomXPos = rand.nextInt(screen.getNumColumns());
                randomYPos = rand.nextInt(screen.getNumRows());
            }

            takenPositions.add(curPosition);

            RegularReward cur = new RegularReward((randomXPos) * screen.getTileSize(),
                    (randomYPos) * screen.getTileSize());
            screen.setReward(i, cur);
        }
    }
}
