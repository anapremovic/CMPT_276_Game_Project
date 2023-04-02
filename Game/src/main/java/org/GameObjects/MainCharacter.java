package org.GameObjects;

import org.Display.*;
import org.Input.*;
import org.Logic.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Entity used to encode all information on our game's main character, controlled by the player: a turtle trying to
 * reach the ocean.
 */
public class MainCharacter extends MovableObject{
    /**
     * Current number of carrots (regular rewards) collected by the player.
     */
    private int numCarrotsCollected;

    /**
     * Screen the main character is displayed on.
     */
    private Screen screen;

    /**
     * Object to take user input which facilitates movement.
     */
    private KeyHandler playerInput;

    /**
     * Object to handle collisions with immovable objects and tiles.
     */
    private CollisionDetector collisionDetector;

    /**
     * Object which displays all immovable objects to screen.
     */
    private ImmovableObjectDisplay objDisplayer;

    /**
     * Object which contains information on game tiles.
     */
    private TileManager tileM;

    /**
     * Object containing logic to display an image on the screen from a PNG file. There are four possible turtle
     * sprites, depending on which direction the player is facing.
     */
    private BufferedImage up, down, left, right;

    /**
     * Indicates which way the player is facing.
     */
    private String direction;

    /**
     * Starting x position of the player.
     */
    private int initialXPos;

    /**
     * Starting y position of the player.
     */
    private int initialYPos;

    /**
     * Indicates whether the main game thread has ended or not.
     */
    private boolean hasGameEnded = false;

    /**
     * Initializes all main character variables, sets the initial position of the player, sets the starting direction
     * faced and speed, and loads the turtle sprite.
     *
     * @param screen                screen main character is displayed on
     * @param handler               object that handles user input
     * @param collisionDetector     object that handles collisions with immovable objects and tiles
     * @param objDisplayer          object that display immovable objects to screen
     * @param tileM                 object that contains information on tiles
     */
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

    /**
     * Set default values for the main character, including position, speed, and direction faced.
     *
     * @param speed         how many pixels the main character moves when a key is pressed to move
     * @param direction     initial direction the main character is facing
     */
    public void setStartingValues(int speed, String direction) {
        this.xPos = initialXPos;
        this.yPos = initialYPos;
        this.speed = speed;
        this.direction = direction;
    }

    /**
     * Load all four turtle sprites.
     */
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

    /**
     * Handles logic for when the player touches an immovable object.
     *
     * @param objectType    index representing the type of the object corresponding to the objects[] array in Screen
     */
    public void touchObject(int objectType) {
        if(objectType != 999) { // when index is 999, no object was touched
            String objectName = screen.getObjects()[objectType].getName();

            int x;
            int y;
            switch(objectName) {
                case "Carrot":
                    // increment score by 1
                    this.numCarrotsCollected += 1;
                    screen.updateScore(1);

                    // remove carrot from screen
                    x = screen.getObjects()[objectType].getXPos();
                    y = screen.getObjects()[objectType].getYPos();
                    screen.setObject(objectType, null);
                    objDisplayer.removeTakenPosition(x, y);

                    break;
                case "Mystical Ocean Fruit":
                    // increment score by 3
                    screen.updateScore(3);

                    // remove bonus reward from screen
                    x = screen.getObjects()[objectType].getXPos();
                    y = screen.getObjects()[objectType].getYPos();
                    screen.setObject(objectType, null);
                    objDisplayer.removeTakenPosition(x, y);

                    break;
                case "Lava":
                    // decrement score by 3
                    this.numCarrotsCollected -= 3;
                    screen.updateScore(-3);

                    // put player back to start
                    this.setPosition(this.initialXPos, this.initialYPos);

                    // remove lava from screen
                    x = screen.getObjects()[objectType].getXPos();
                    y = screen.getObjects()[objectType].getYPos();
                    screen.setObject(objectType, null);
                    objDisplayer.removeTakenPosition(x, y);

                    // ADD 3 NEW CARROTS TO SCREEN

                    Random rand = new Random();
                    int[][] board = tileM.getBoard();

                    int count = 0; // number of carrots added
                    int i = 0; // index for objects[] in Screen
                    while(count < 3 && i < screen.getObjects().length) {
                        if(screen.getObjects()[i] == null) {
                            // generate random position: x and y
                            int[] curPos = objDisplayer.generateRandomPosition();
                            int randomXPos = curPos[0];
                            int randomYPos = curPos[1];

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

    /**
     * Handles logic for when player touches an ocean tile.
     *
     * @param tileType      index representing the type of the object corresponding to the objects[] array in Screen
     */
    public void exitCave(int tileType) {
        if(tileType == 3 && !hasGameEnded) {
            // the player has won so end the game loop
            screen.endGameThread();

            long seconds = screen.getElapsedTime() / 1000; // time when player won

            // show winning screen
            screen.initializeWinningMenu(seconds, screen, numCarrotsCollected);
            screen.getWinningMenu().displayWinningMenu();;
            hasGameEnded = true;
        }
    }

    /**
     * Updates player position and sprite on key press.
     */
    public void update() {
        // is the player colliding?
        this.collisionOn = false;

        int tileTypeTouched;
        if(playerInput.upPressed) {
            direction = "up";

            // update collisionOn
            tileTypeTouched = collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);

            touchObject(objIndex); // invoke corresponding logic depending on which tile is being touched
            exitCave(tileTypeTouched); // if touching ocean tile, win game

            // if not colliding, move
            if(this.collisionOn == false) {
                this.updateYPos(this.getSpeed());
            }
        }
        else if(playerInput.downPressed) {
            direction = "down";

            // update collisionOn
            tileTypeTouched = collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);

            touchObject(objIndex); // invoke corresponding logic depending on which tile is being touched
            exitCave(tileTypeTouched); // if touching ocean tile, win game

            // if not colliding, move
            if(this.collisionOn == false) {
                this.updateYPos(-1 * this.getSpeed());
            }
        }
        else if(playerInput.rightPressed) {
            direction = "right";

            // update collisionOn
            tileTypeTouched = collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);

            touchObject(objIndex); // invoke corresponding logic depending on which tile is being touched
            exitCave(tileTypeTouched); // if touching ocean tile, win game

            // if not colliding, move
            if(this.collisionOn == false) {
                this.updateXPos(this.getSpeed());
            }
        }
        else if(playerInput.leftPressed) {
            direction = "left";

            // update collisionOn
            tileTypeTouched = collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);

            touchObject(objIndex); // invoke corresponding logic depending on which tile is being touched
            exitCave(tileTypeTouched); // if touching ocean tile, win game

            // if not colliding, move
            if(this.collisionOn == false) {
                this.updateXPos(-1 * this.getSpeed());
            }
        }
    }

    /**
     * Display correct turtle sprite to screen, depending on direction faced
     * @param g     the <code>Graphics</code> object to protect
     */
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

        // draw corresponding sprite
        g.drawImage(img, xPos, yPos, width, height, null);
    }

    // GETTERS

    public int getNumCarrotsCollected() { return numCarrotsCollected; }
    public String getDirection() { return direction; }
}

