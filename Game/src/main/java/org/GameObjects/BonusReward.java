package org.GameObjects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BonusReward extends Reward {
    private double timeSinceSpawned;

    public BonusReward(int xPos, int yPos) {
        timeSinceSpawned = 0;

        this.xPos = xPos;
        this.yPos = yPos;

        //this.points = points;
        this.name = "Mystical Ocean Fruit";

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

    public double getTime() {
        return timeSinceSpawned;
    }
}
