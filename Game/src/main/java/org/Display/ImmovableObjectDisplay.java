package org.Display;

import org.GameObjects.*;

import java.util.ArrayList;
import java.util.Random;

public class ImmovableObjectDisplay {
    private Screen screen;
    private TileManager tileM;
    ArrayList<int[]> takenPositions;

    public ImmovableObjectDisplay(Screen screen, TileManager tileM, MainCharacter mc) {
        this.screen = screen;
        this.tileM = tileM;
        takenPositions = new ArrayList<int[]>(); // all positions that have an immovable object on them

        // set turtle's initial position to taken
        int[] initialPosition = new int[2];
        initialPosition[0] = mc.getInitialXPos();
        initialPosition[1] = mc.getInitialYPos();
        takenPositions.add(initialPosition);
    }

    public void displayObjects() {
        Random rand = new Random();

        int[][] board = tileM.getBoard(); // game board determining tile types
        int numObjects = screen.getObjects().length;

        // generate 10 carrots and 1 mystical ocean fruit
        for(int i = 0; i < numObjects; i++) {
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
                System.out.println("Regenerate position");
                // regenerate position
                randomXPos = rand.nextInt(screen.getNumColumns());
                randomYPos = rand.nextInt(screen.getNumRows());

                positionTaken = false;
                for(int j = 0; j < takenPositions.size(); j++) {
                    if(takenPositions.get(j)[0] == randomXPos && takenPositions.get(j)[1] == randomYPos) {
                        positionTaken = true;
                    }
                }
            }

            // add the current position to the list of taken positions
            int[] curPosition = new int[2];
            curPosition[0] = randomXPos;
            curPosition[1] = randomYPos;
            takenPositions.add(curPosition);

            if(i == 10) {
                // display bonus reward (mystical ocean fruit) at current position
                BonusReward cur = new BonusReward((randomXPos) * screen.getTileSize(),
                        (randomYPos) * screen.getTileSize());
                screen.setObject(i, cur);
            }
            else if(i >= 11) {
                // display lava on screen at current position
                Punishment cur = new Punishment((randomXPos) * screen.getTileSize(),
                        (randomYPos) * screen.getTileSize());
                screen.setObject(i, cur);
            }
            else {
                // display carrot on screen at current position
                RegularReward cur = new RegularReward((randomXPos) * screen.getTileSize(),
                        (randomYPos) * screen.getTileSize());
                screen.setObject(i, cur);
            }
        }
    }
}
