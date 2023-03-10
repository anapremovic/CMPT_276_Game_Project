package org.GameObjects;

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

    public MainCharacter(int initialXPos, int initialYPos, int width, int height) {
        this.xPos = initialXPos;
        this.yPos = initialYPos;
        this.width = width;
        this.height = height;
    }

    public void tick() {

    }

    public void Render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(xPos, yPos, width, height);

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

    public void acceptUserInput() {

        // implement me
    }

    public void updatePosition() {
        // implement me
    }
}

