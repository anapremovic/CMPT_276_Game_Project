package org.GameObjects;

import org.Display.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Abstract class representing any object on the screen that is static, including carrots, mystical ocean fruits, and
 * lava tiles.
 */
public abstract class ImmovableObject {

    /**
     * Current x position of this object.
     */
    protected int xPos;

    /**
     * Current y position of this object.
     */
    protected int yPos;

    /**
     * Object containing logic to display an image on the screen from a PNG file.
     */
    protected BufferedImage image;

    /**
     * Indicates whether this immovable object is "solid" or not. In other words, whether movable objects can pass
     * through it.
     */
    protected boolean collision = false;

    /**
     * A name describing this immovable object ("Carrot", "Mystical Ocean Fruit", or "Lava")
     */
    protected String name;

    /**
     * Area of the object that triggers collisions.
     */
    protected Rectangle collidableArea;

    /**
     * The initial collidable length and width.
     */
    protected int collidableAreaDefaultX, collidableAreaDefaultY;

    /**
     * How long since this object has been displayed on the screen, in milliseconds.
     */
    protected long timeSinceSpawned = 0;

    /**
     * Display this object on the screen.
     *
     * @param g         the <code>Graphics</code> object to protect
     * @param screen    the screen to display the object on
     */
    public void draw(Graphics2D g, Screen screen) {
        g.drawImage(image, xPos, yPos, screen.getTileSize(), screen.getTileSize(), null);
    }

    // GETTERS

    public int getXPos() { return xPos; }
    public int getYPos() { return yPos; }
    public boolean isSolid() { return collision; };
    public String getName() { return name; }
    public Rectangle getCollidableArea() { return collidableArea; }
    public int getCollidableAreaDefaultX() { return collidableAreaDefaultX; }
    public int getCollidableAreaDefaultY() { return collidableAreaDefaultY; }
    public long getTime() { return timeSinceSpawned; }

    // SETTERS
    public void setCollidableAreaX(int x) {
        collidableArea.x = x;
    }
    public void setCollidableAreaY(int y) {
        collidableArea.y = y;
    }
    public void updateTime(long addedTime) {
        timeSinceSpawned = addedTime;
    }
}
