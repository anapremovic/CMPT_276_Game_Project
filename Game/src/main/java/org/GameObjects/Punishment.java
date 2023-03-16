package org.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Entity encoding information on game punishments: lava tiles which cause the player to lose points and return to the
 * starting position when touched.
 */
public class Punishment extends ImmovableObject {

    /**
     * Initialize this punishment to a given x and y position, as well as initializing all its variables and loading
     * its image file.
     *
     * @param xPos      x position of punishment on screen
     * @param yPos      y position of punishment on screen
     */
    public Punishment(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;

        this.name = "Lava";

        this.collidableArea = new Rectangle(0, 0, 48, 48);
        this.collidableAreaDefaultX = collidableArea.x;
        this.collidableAreaDefaultY = collidableArea.y;

        // load lava image
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/lava.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
