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
        System.out.println("X Pos updated by " + updateBy);
    }

    public void updateYPos(int updateBy) {
        yPos -= updateBy;
        System.out.println("Y Pos updated by " + updateBy);
    }
}
