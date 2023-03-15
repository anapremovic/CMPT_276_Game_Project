package org.Display;

import org.GameObjects.*;

import java.util.ArrayList;
import java.util.Random;

public class ImmovableObjectDisplay extends Thread {
    private Screen screen;
    private TileManager tileM;
    private ArrayList<int[]> takenPositions;
    private Thread bonusRewardThread;
    private boolean showingBonusReward = false;

    public ImmovableObjectDisplay(Screen screen, TileManager tileM) {
        this.screen = screen;
        this.tileM = tileM;
        takenPositions = new ArrayList<int[]>(); // all positions that have an immovable object on them

        // set turtle's initial position to taken
        int[] initialPosition = new int[2];
        initialPosition[0] = 2;
        initialPosition[1] = 14;
        takenPositions.add(initialPosition);

        // set 3 positions in front of exit to taken
        int[] inFrontOfExit1 = new int[2];
        inFrontOfExit1[0] = 17;
        inFrontOfExit1[1] = 1;
        takenPositions.add(inFrontOfExit1);
        int[] inFrontOfExit2 = new int[2];
        inFrontOfExit2[0] = 18;
        inFrontOfExit2[1] = 1;
        takenPositions.add(inFrontOfExit2);
        int[] inFrontOfExit3 = new int[2];
        inFrontOfExit3[0] = 16;
        inFrontOfExit3[1] = 1;
        takenPositions.add(inFrontOfExit3);
    }

    public void displayObjects(int numToDisplay) {
        Random rand = new Random();

        int[][] board = tileM.getBoard(); // game board determining tile types
        //int numObjects = screen.getObjects().length;

        // generate 10 carrots and 1 mystical ocean fruit
        for(int i = 0; i < numToDisplay; i++) {
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

    public ArrayList<int[]> getTakenPositions() { return takenPositions; }
    public void addTakenPosition(int x, int y) {
        int[] newPosition = new int[2];
        newPosition[0] = x;
        newPosition[1] = y;

        takenPositions.add(newPosition);
    }
    public void removeTakenPosition(int x, int y) {
        for(int[] cur : takenPositions) {
            if(cur[0] == x && cur[1] == y) {
                takenPositions.remove(cur);
            }
        }
    }
}
