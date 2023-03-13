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

    protected Rectangle collidableArea; // the area of the object that triggers collisions
    protected boolean collisionOn = false;

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public Rectangle getCollidableArea() { return collidableArea; }

    public boolean isCollisionOn() { return collisionOn; }

    public void draw(Graphics2D g, Screen screen) {
        g.drawImage(image, xPos, yPos, screen.getTileSize(), screen.getTileSize(), null);
    }
}
