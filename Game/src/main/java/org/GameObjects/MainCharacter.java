package org.GameObjects;

import org.Display.*;
import org.Input.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainCharacter extends MovableObject{
    private int points;
    private boolean isInvisible;
    private Screen screen;
    private KeyHandler playerInput;

    public MainCharacter(int width, int height, Screen s, KeyHandler keyH) {
        this.width = width;
        this.height = height;
        this.screen = s;
        this. playerInput = keyH;

        setStartingValues(100, 100, 4);
    }

    public void setStartingValues(int initialXPos, int initialYPos, int speed) {
        this.xPos = initialXPos;
        this.yPos = initialYPos;
        this.speed = speed;
    }

    public void update() {
        if(playerInput.upPressed) {
            this.updateYPos(this.getSpeed());
        }
        else if(playerInput.downPressed) {
            this.updateYPos(-1 * this.getSpeed());
        }
        else if(playerInput.rightPressed) {
            this.updateXPos(this.getSpeed());
        }
        else if(playerInput.leftPressed) {
            this.updateXPos(-1 * this.getSpeed());
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(this.getXPos(), this.getYPos(), screen.getTileSize(), screen.getTileSize());
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

