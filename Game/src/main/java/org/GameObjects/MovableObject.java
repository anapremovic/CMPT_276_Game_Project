package org.GameObjects;

public abstract class MovableObject {
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;

    public abstract void updatePosition();

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
