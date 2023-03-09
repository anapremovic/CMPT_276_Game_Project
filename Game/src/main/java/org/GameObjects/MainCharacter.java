package org.GameObjects;

public class MainCharacter extends MovableObject{
    private int points;
    private boolean isInvisible;

    public MainCharacter(int initialXPos, int initialYPos, int width, int height) {
        this.xPos = initialXPos;
        this.yPos = initialYPos;
        this.width = width;
        this.height = height;
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

