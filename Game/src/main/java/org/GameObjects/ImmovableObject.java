package org.GameObjects;

import org.Display.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ImmovableObject {
    protected int xPos;
    protected int yPos;
    protected BufferedImage image;
    protected boolean collision = false;
    protected String name;


    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void draw(Graphics2D g, Screen screen) {
        g.drawImage(image, xPos, yPos, screen.getTileSize(), screen.getTileSize(), null);
    }
}
