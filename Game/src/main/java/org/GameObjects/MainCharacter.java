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
        /*
        try {
            InputStream turtle = getClass().getResourceAsStream("resources/turtle.png");
            ImageIcon turtleIcon = new ImageIcon(ImageIO.read(turtle).getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        } catch (
        IOException e) {
            e.printStackTrace();
        }*/

        BufferedImage turtle = null;
        try {
            turtle = ImageIO.read(new File("2d-game\\Game\\src\\main\\java\\org\\GameObjects\\turtle.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        g.drawImage(turtle, xPos, yPos, null);

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

