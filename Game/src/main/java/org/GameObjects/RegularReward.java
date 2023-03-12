package org.GameObjects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class RegularReward extends Reward {
    public RegularReward(int points) {
        this.points = points;
        this.name = "Carrot";

        // load carrot image
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/carrot.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public int getPoints() {
        return points;
    }
}
