package org.GameObjects;

import java.awt.*;

/**
 * Abstract class representing any object on the screen that can move, like the main character and enemy objects.
 */
public abstract class MovableObject {

    /**
     * Current x position of this object.
     */
    protected int xPos;

    /**
     * Current y position of this object.
     */
    protected int yPos;

    /**
     * Width of this object in pixels.
     */
    protected int width;

    /**
     * Height of this object in pixels.
     */
    protected int height;

    /**
     * How many pixels this object moves at each "movement" (user key press or tick).
     */
    protected int speed;

    /**
     * Area of the object that triggers collisions.
     */
    protected Rectangle collidableArea;

    /**
     * The initial collidable length and width.
     */
    protected int collidableAreaDefaultX, collidableAreaDefaultY;

    /**
     * Indicates whether this object is currently colliding with another object.
     */
    protected boolean collisionOn = false;

    // GETTERS

    public int getXPos() { return xPos; }
    public int getYPos() { return yPos; }
    public int getSpeed() { return speed; }
    public Rectangle getCollidableArea() { return collidableArea; }
    public int getCollidableAreaDefaultX() { return collidableAreaDefaultX; }
    public int getCollidableAreaDefaultY() { return collidableAreaDefaultY; }

    // SETTERS

    public void setCollidableAreaX(int x) {
        collidableArea.x = x;
    }
    public void setCollidableAreaY(int y) {
        collidableArea.y = y;
    }
    public void setCollisionOn(boolean setTo) {
        collisionOn = setTo;
    }
    public void updateXPos(int updateBy) {
        xPos += updateBy;
    }
    public void updateYPos(int updateBy) {
        yPos -= updateBy;
    }
    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }
}
