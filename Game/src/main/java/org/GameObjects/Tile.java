package org.GameObjects;

import java.awt.image.BufferedImage;

/**
 * Entity encoding information on game tiles which make up the board that the game takes place on.
 */
public class Tile {

    /**
     * Object containing logic to display an image on the screen from a PNG file.
     */
    public BufferedImage image;

    /**
     * Indicates whether this tile is "solid" or not. In other words, whether movable objects can pass through it.
     */
    public boolean collision = false;
}
