package org.Logic;

import org.GameObjects.*;
import org.Display.*;

public class CollisionDetector {

    Screen screen;
    TileManager tileM;
    MainCharacter object1;
    Object object2;

    public CollisionDetector(Screen screen, TileManager tileM) {
        this.screen = screen;
        this.tileM = tileM;
    }

    CollisionDetector(MainCharacter o1, ImmovableObject o2) {
        object1 = o1;
        object2 = o2;
    }

    CollisionDetector(MainCharacter o1, MovableObject o2) {
        object1 = o1;
        object2 = o2;
    }

    public void detectTile(MainCharacter player) {
        // x and y positions in pixels
        int leftX = player.getXPos() + player.getCollidableArea().x;
        int rightX = player.getXPos() + player.getCollidableArea().x + player.getCollidableArea().width;
        int topY = player.getYPos() + player.getCollidableArea().y;
        int bottomY = player.getYPos() + player.getCollidableArea().y + player.getCollidableArea().height;

        // x and y positions in terms of columns
        int leftCol = leftX/screen.getTileSize();
        int rightCol = rightX/screen.getTileSize();
        int topRow = topY/screen.getTileSize();
        int bottomRow = bottomY/screen.getTileSize();

        // the tile types the player is colliding with
        // since MainCharacter is 1x1 tile, it can only be colliding with a max of 2 tiles at once
        int tileType1, tileType2;

        String direction = player.getDirection(); // direction player is facing
        switch(direction) {
            case "up":
                topRow = (topY - player.getSpeed())/screen.getTileSize();
                tileType1 = tileM.getBoard()[leftCol][topRow]; // left side of player
                tileType2 = tileM.getBoard()[rightCol][topRow]; // right side of player

                // player is hitting a solid tile, set collisionOn to true
                if(tileM.getTileTypes()[tileType1].collision == true || tileM.getTileTypes()[tileType2].collision == true) {
                    player.setCollisionOn(true);
                }

                break;
            case "down":
                bottomRow = (bottomY + player.getSpeed())/screen.getTileSize();
                tileType1 = tileM.getBoard()[leftCol][bottomRow]; // left side of player
                tileType2 = tileM.getBoard()[rightCol][bottomRow]; // right side of player

                // player is hitting a solid tile, set collisionOn to true
                if(tileM.getTileTypes()[tileType1].collision == true || tileM.getTileTypes()[tileType2].collision == true) {
                    player.setCollisionOn(true);
                }

                break;
            case "left":
                leftCol = (leftX - player.getSpeed())/screen.getTileSize();
                tileType1 = tileM.getBoard()[leftCol][topRow]; // top side of player
                tileType2 = tileM.getBoard()[leftCol][bottomRow]; // bottom side of player

                // player is hitting a solid tile, set collisionOn to true
                if(tileM.getTileTypes()[tileType1].collision == true || tileM.getTileTypes()[tileType2].collision == true) {
                    player.setCollisionOn(true);
                }

                break;
            case "right":
                rightCol = (rightX + player.getSpeed())/screen.getTileSize();
                tileType1 = tileM.getBoard()[rightCol][topRow]; // top side of player
                tileType2 = tileM.getBoard()[rightCol][bottomRow]; // bottom side of player

                // player is hitting a solid tile, set collisionOn to true
                if(tileM.getTileTypes()[tileType1].collision == true || tileM.getTileTypes()[tileType2].collision == true) {
                    player.setCollisionOn(true);
                }

                break;
        }
    }



    boolean detectCollision() {
        if (object2 instanceof ImmovableObject) {
            ImmovableObject temp = (ImmovableObject) object2;
            return object1.getXPos() == temp.getXPos() && object1.getYPos() == temp.getYPos();
        }
        if (object2 instanceof MovableObject) {
            MovableObject temp = (MovableObject) object2;
            return object1.getXPos() == temp.getXPos() && object1.getYPos() == temp.getYPos();
        }
        return false;
    }
}
