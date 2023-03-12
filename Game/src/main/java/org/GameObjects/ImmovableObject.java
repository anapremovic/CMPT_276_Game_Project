package org.GameObjects;

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
}
