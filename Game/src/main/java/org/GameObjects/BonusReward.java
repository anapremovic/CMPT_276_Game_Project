package org.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Entity used to encode all information regarding the bonus reward, a specific type of reward which is not required
 * to win the game, but increases the final score of the player.
 */
public class BonusReward extends Reward {
    /**
     * Initialize this bonus reward to a given x and y position, as well as initializing all its variables and loading
     * its image file.
     *
     * @param xPos  x position of the bonus reward on screen
     * @param yPos  y position of the bonus reward on screen
     */
    public BonusReward(int xPos, int yPos) {
        this.timeSinceSpawned = 0;

        this.xPos = xPos;
        this.yPos = yPos;

        //this.points = points;
        this.name = "Mystical Ocean Fruit";

        this.collidableArea = new Rectangle(0, 0, 48, 48);
        this.collidableAreaDefaultX = collidableArea.x;
        this.collidableAreaDefaultY = collidableArea.y;

        // load fruit image
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/mystical_ocean_fruit.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // GETTERS
    public int getPoints() {
        return points;
    }
}
