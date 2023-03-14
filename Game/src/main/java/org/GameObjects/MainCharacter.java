package org.GameObjects;

import org.Display.*;
import org.Input.*;
import org.Logic.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainCharacter extends MovableObject{
    private int points; // current player points
    private boolean isInvisible; // has the player achieve Mystical Ocean Fruit powerup
    private Screen screen; // Screen class the main character is draw on
    private KeyHandler playerInput; // object that takes user input
    private CollisionDetector collisionDetector; // handles collisions with other objects

    private BufferedImage up, down, left, right; // 4 possible turtle sprites
    private String direction; // which way the turtle is facing

    public MainCharacter(Screen screen, KeyHandler handler, CollisionDetector collisionDetector) {
        this.width = screen.getTileSize();
        this.height = screen.getTileSize();
        this.screen = screen;
        this.playerInput = handler;
        this.collisionDetector = collisionDetector;

        this.collidableArea = new Rectangle(10, 10, 28, 28);
        this.collidableAreaDefaultX = collidableArea.x;
        this.collidableAreaDefaultY = collidableArea.y;

        setStartingValues(100, 100, 4, "up");
        getImage();
    }

    // set default values for the main character, including position, speed, and direction faced
    public void setStartingValues(int initialXPos, int initialYPos, int speed, String direction) {
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
    public void touchObject(int index) {
        if(index != 999) { // when index is 999, no object was touched
            String objectName = screen.getRewards()[index].getName();



            switch(objectName) {
                case "Carrot":
                    this.points += 1;
                    screen.updateScore(1);
                    screen.setReward(index, null);
                    break;
                case "Mystical Ocean Fruit":
                    this.points += 3;
                    screen.updateScore(3);
                    screen.setReward(index, null);
                    break;
            }
        }
    }

    // update player position and sprite on key press
    public void update() {
        // is the player colliding?
        this.collisionOn = false;

        if(playerInput.upPressed) {
            direction = "up";
            collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);
            touchObject(objIndex);
            if(this.collisionOn == false) {
                this.updateYPos(this.getSpeed());
            }
        }
        else if(playerInput.downPressed) {
            direction = "down";
            collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);
            touchObject(objIndex);
            if(this.collisionOn == false) {
                this.updateYPos(-1 * this.getSpeed());
            }
        }
        else if(playerInput.rightPressed) {
            direction = "right";
            collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);
            touchObject(objIndex);
            if(this.collisionOn == false) {
                this.updateXPos(this.getSpeed());
            }
        }
        else if(playerInput.leftPressed) {
            direction = "left";
            collisionDetector.detectTile(this);
            int objIndex = collisionDetector.detectImmovableObject(this);
            touchObject(objIndex);
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

