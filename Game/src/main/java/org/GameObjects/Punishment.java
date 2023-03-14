package org.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Punishment extends ImmovableObject {
    private int damage;

    public Punishment(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.damage = 3;

        //this.points = points;
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
