package org.GameObjects;

import java.awt.*;

public abstract class MovableObject {
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected int speed;

    protected Rectangle collidableArea; // the area of the object that triggers collisions
    protected int collidableAreaDefaultX, collidableAreaDefaultY;
    protected boolean collisionOn = false;
    public int getXPos() {
        return xPos;
    }

    public int getYPos() { return yPos; }

    public int getSpeed() { return speed; }

    public Rectangle getCollidableArea() { return collidableArea; }
    public int getCollidableAreaDefaultX() { return collidableAreaDefaultX; }
    public int getCollidableAreaDefaultY() { return collidableAreaDefaultY; }

    public void setCollidableAreaX(int x) {
        collidableArea.x = x;
    }
    public void setCollidableAreaY(int y) {
        collidableArea.y = y;
    }

    public boolean isCollisionOn() { return collisionOn; }

    public void setCollisionOn(boolean setTo) {
        collisionOn = setTo;
    }

    public void updateXPos(int updateBy) {
        xPos += updateBy;
    }

    public void updateYPos(int updateBy) {
        yPos -= updateBy;
    }
}
