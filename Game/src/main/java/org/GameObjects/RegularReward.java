package org.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Entity encoding information on game punishments: carrots which grant the player points. The player must collect
 * all 10 carrots in order to escape the cave and win the game.
 */
public class RegularReward extends Reward {

    /**
     * Initialize this reward to a given x and y position, as well as initializing all its variables and loading
     * its image file.
     *
     * @param xPos      x position of punishment on screen
     * @param yPos      y position of punishment on screen
     */
    public RegularReward(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;

        //this.points = points;
        this.name = "Carrot";

        this.collidableArea = new Rectangle(0, 0, 48, 48);
        this.collidableAreaDefaultX = collidableArea.x;
        this.collidableAreaDefaultY = collidableArea.y;

        // load carrot image
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/carrot.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // GETTERS
    public int getPoints() { return points; }
}
