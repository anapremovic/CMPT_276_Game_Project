package org.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class RegularReward extends Reward {
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
    public int getPoints() {
        return points;
    }
}
