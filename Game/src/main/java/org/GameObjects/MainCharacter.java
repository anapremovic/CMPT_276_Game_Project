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

        collidableArea = new Rectangle(8 , 8, 32, 32);

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

    // update player position and sprite on key press
    public void update() {
        // UPDATE POSITION BASED ON USER INPUT
        if(playerInput.upPressed) {
            direction = "up";
            this.updateYPos(this.getSpeed());
        }
        else if(playerInput.downPressed) {
            direction = "down";
            this.updateYPos(-1 * this.getSpeed());
        }
        else if(playerInput.rightPressed) {
            direction = "right";
            this.updateXPos(this.getSpeed());
        }
        else if(playerInput.leftPressed) {
            direction = "left";
            this.updateXPos(-1 * this.getSpeed());
        }

        // UPDATE POSITION BASED ON COLLISIONS
        this.collisionOn = false;
        collisionDetector.detectTile(this);

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

//        g2.setColor(Color.white);
//        g2.fillRect(xPos, yPos, screen.getTileSize(), screen.getTileSize());
    }

    public int getPoints() {
        return points;
    }

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

