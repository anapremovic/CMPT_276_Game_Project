package org.GameObjects;

import org.Display.*;
import org.Input.*;
import org.Logic.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class MainCharacter extends MovableObject{
    private int points; // current player points
    private boolean isInvisible; // has the player achieve Mystical Ocean Fruit powerup
    private Screen screen; // Screen class the main character is draw on
    private KeyHandler playerInput; // object that takes user input
    private CollisionDetector collisionDetector; // handles collisions with other objects
    private ImmovableObjectDisplay objDisplayer;
    private TileManager tileM;

    private BufferedImage up, down, left, right; // 4 possible turtle sprites
    private String direction; // which way the turtle is facing

    private int initialXPos;
    private int initialYPos;

    public MainCharacter(Screen screen, KeyHandler handler, CollisionDetector collisionDetector,
                         ImmovableObjectDisplay objDisplayer, TileManager tileM) {
        this.width = screen.getTileSize();
        this.height = screen.getTileSize();
        this.screen = screen;
        this.playerInput = handler;
        this.collisionDetector = collisionDetector;
        this.objDisplayer = objDisplayer;
        this.tileM = tileM;

        this.collidableArea = new Rectangle(10, 10, 28, 28);
        this.collidableAreaDefaultX = collidableArea.x;
        this.collidableAreaDefaultY = collidableArea.y;

        this.initialXPos = 2 * screen.getTileSize();
        this.initialYPos = 14 * screen.getTileSize();

        setStartingValues(4, "up");
        getImage();
    }

    // set default values for the main character, including position, speed, and direction faced
    public void setStartingValues(int speed, String direction) {
        this.xPos = initialXPos;
        this.yPos = initialYPos;
        this.speed = speed;
        this.direction = direction;
    }

    // load turtle sprite on to the screen
    public void getImage() {
        try {
            up = ImageIO.read(getClass().getResourceAsStream("/turtle_up.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/turtle_down.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/turtle_left.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/turtle_right.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // functionality for when the player touches a regular reward, bonus reward, or punishment
    private void touchObject(int index) {
        if(index != 999) { // when index is 999, no object was touched
            String objectName = screen.getObjects()[index].getName();

            int x;
            int y;
            switch(objectName) {
                case "Carrot":
                    this.points += 1;
                    screen.updateScore(1);

                    x = screen.getObjects()[index].getXPos();
                    y = screen.getObjects()[index].getYPos();

                    screen.setObject(index, null);
                    objDisplayer.removeTakenPosition(x, y);

                    break;
                case "Mystical Ocean Fruit":
                    this.points += 3;
                    screen.updateScore(3);

                    x = screen.getObjects()[index].getXPos();
                    y = screen.getObjects()[index].getYPos();

                    screen.setObject(index, null);
                    objDisplayer.removeTakenPosition(x, y);

                    break;
                case "Lava":
                    this.points -= 3;
                    screen.updateScore(-3);
                    this.setPosition(this.initialXPos, this.initialYPos);

                    // ADD 3 NEW CARROTS TO SCREEN

                    Random rand = new Random();
                    int[][] board = tileM.getBoard();

                    int count = 0; // number of carrots added
                    int i = 0; // index for objects[] in Screen
                    while(count < 3 && i < screen.getObjects().length) {
                        if(screen.getObjects()[i] == null) {
                            // random x and y
                            int randomXPos = rand.nextInt(screen.getNumColumns());
                            int randomYPos = rand.nextInt(screen.getNumRows());

                            // for checking if the current position is taken by another object
                            boolean positionTaken = false;
                            for(int j = 0; j < objDisplayer.getTakenPositions().size(); j++) {
                                if(objDisplayer.getTakenPositions().get(j)[0] == randomXPos &&
                                        objDisplayer.getTakenPositions().get(j)[1] == randomYPos) {
                                    positionTaken = true;
                                }
                            }

                            // if generated position is not a cave tile, or taken, regenerate it
                            while(tileM.getBoard()[randomXPos][randomYPos] != 0 || positionTaken) {
                                // regenerate
                                randomXPos = rand.nextInt(screen.getNumColumns());
                                randomYPos = rand.nextInt(screen.getNumRows());

                                // reset positionTaken with regenerated values
                                positionTaken = false;
                                for(int j = 0; j < objDisplayer.getTakenPositions().size(); j++) {
                                    if(objDisplayer.getTakenPositions().get(j)[0] == randomXPos &&
                                            objDisplayer.getTakenPositions().get(j)[1] == randomYPos) {
                                        positionTaken = true;
                                    }
                                }
                            }

                            // set position as taken
                            objDisplayer.addTakenPosition(randomXPos, randomYPos);

                            // display new carrot at generated position
                            RegularReward cur = new RegularReward(randomXPos * screen.getTileSize(),
                                    randomYPos * screen.getTileSize());
                            screen.setObject(i, cur);

                            // increase carrot count (out of 3)
                            count++;
                        }

                        // increment objects[] index
                        i++;
                    }
            }
        }
    }

    // functionality for when player touches an ocean tile
    private void exitCave(int tileType) {
        if(tileType == 3) {
            screen.endGameThread();
            long seconds = screen.getElapsedTime() / 1000;
            screen.initializeWinningMenu(seconds, screen);
            screen.getWinningMenu().displayWinningMenu();
        }
    }

    // update player position and sprite on key press
    public void update() {
        // is the player colliding?
        this.collisionOn = false;

        int tileTypeTouched;
        if(playerInput.upPressed) {
            direction = "up";
            tileTypeTouched = collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);
            touchObject(objIndex);
            exitCave(tileTypeTouched);
            if(this.collisionOn == false) {
                this.updateYPos(this.getSpeed());
            }
        }
        else if(playerInput.downPressed) {
            direction = "down";
            tileTypeTouched = collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);
            touchObject(objIndex);
            exitCave(tileTypeTouched);
            if(this.collisionOn == false) {
                this.updateYPos(-1 * this.getSpeed());
            }
        }
        else if(playerInput.rightPressed) {
            direction = "right";
            tileTypeTouched = collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);
            touchObject(objIndex);
            exitCave(tileTypeTouched);
            if(this.collisionOn == false) {
                this.updateXPos(this.getSpeed());
            }
        }
        else if(playerInput.leftPressed) {
            direction = "left";
            tileTypeTouched = collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);
            touchObject(objIndex);
            exitCave(tileTypeTouched);
            if(this.collisionOn == false) {
                this.updateXPos(-1 * this.getSpeed());
            }
        }
    }

    // draw the turtle sprite image on to the screen
    public void draw(Graphics2D g) {
        BufferedImage img = null;

        // choose correct sprite based on direction faced
        switch(direction) {
            case "up":
                img = up;
                break;
            case "down":
                img = down;
                break;
            case "left":
                img = left;
                break;
            case "right":
                img = right;
                break;
        }

        g.drawImage(img, xPos, yPos, width, height, null);
    }

    public int getPoints() {
        return points;
    }

    public String getDirection() { return direction; }

    public int getInitialXPos() { return initialXPos; }
    public int getInitialYPos() { return initialYPos; }

    public void setPoints(int p) {
        points = p;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public void setInvisibility(boolean status) {
        isInvisible = status;
    }
}

