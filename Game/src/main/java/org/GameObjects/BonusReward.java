package org.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class BonusReward extends Reward {
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

    public int getPoints() {
        return points;
    }
}
