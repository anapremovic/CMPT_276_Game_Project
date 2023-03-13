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

    public void displayRewards() {
        ArrayList<int[]> takenPositions = new ArrayList<int[]>(); // all positions that have a reward on them

        Random rand = new Random();

        int[][] board = tileM.getBoard(); // game board determining tile types
        int numRewards = screen.getRewards().length;

        // generate 10 carrots and 1 mystical ocean fruit
        for(int i = 0; i < numRewards; i++) {
            // generate random position for the current reward
            int randomXPos = rand.nextInt(screen.getNumColumns());
            int randomYPos = rand.nextInt(screen.getNumRows());

            // CHECK #1: check to make sure there is not already a carrot at this position
            // CHECK #2: make sure the position is on an empty cave tile

            // prepare boolean for CHECK #1
            boolean positionTaken = false;
            for(int j = 0; j < takenPositions.size(); j++) {
                if(takenPositions.get(j)[0] == randomXPos && takenPositions.get(j)[1] == randomYPos) {
                    positionTaken = true;
                }
            }

            // if both checks not passed, regenerate position
            while(board[randomXPos][randomYPos] != 0 || positionTaken) {
                System.out.print("Regenerate random position - ");
                if(positionTaken) {
                    System.out.println("Position taken");
                }
                else {
                    System.out.println("Tile not empty");
                }

                // regenerate position
                randomXPos = rand.nextInt(screen.getNumColumns());
                randomYPos = rand.nextInt(screen.getNumRows());
            }

            // add the current position to the list of taken positions
            int[] curPosition = new int[2];
            curPosition[0] = randomXPos;
            curPosition[1] = randomYPos;
            takenPositions.add(curPosition);

            if(i == numRewards - 1) {
                // display bonus reward (mystical ocean fruit) at current position
                BonusReward cur = new BonusReward((randomXPos) * screen.getTileSize(),
                        (randomYPos) * screen.getTileSize());
                screen.setReward(i, cur);
            }
            else {
                // display carrot on screen at current position
                RegularReward cur = new RegularReward((randomXPos) * screen.getTileSize(),
                        (randomYPos) * screen.getTileSize());
                screen.setReward(i, cur);
            }
        }
    }
}
