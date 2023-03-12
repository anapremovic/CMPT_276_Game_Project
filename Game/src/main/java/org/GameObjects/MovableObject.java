package org.GameObjects;


public abstract class MovableObject {
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    protected int speed;

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getSpeed() { return speed; }

    public void updateXPos(int updateBy) {
        xPos += updateBy;
    }

    public void updateYPos(int updateBy) {
        yPos -= updateBy;
    }
}
