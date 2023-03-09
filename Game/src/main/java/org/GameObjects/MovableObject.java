package org.GameObjects;

public abstract class MovableObject {
    protected int xPos;
    protected int yPos;

    public abstract void updatePosition();

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
