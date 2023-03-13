package org.Logic;

import org.GameObjects.*;
import org.Display.*;

public class CollisionDetector {
    Screen screen;
    TileManager tileM;

    public CollisionDetector(Screen screen, TileManager tileM) {
        this.screen = screen;
        this.tileM = tileM;
    }
    
    // detect collision between the main character and the tile they are touching
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

    // detect collision between the player and a reward/punishment
    public int detectImmovableObject(MainCharacter player) {
        Reward[] objects = screen.getRewards();

        int index = 999; // index of object in rewards[] in Screen

        for(int i = 0; i < objects.length; i++) {
            if(objects[i] != null) {
                // get the player's collidable area position
                player.setCollidableAreaX(player.getXPos() + player.getCollidableArea().x);
                player.setCollidableAreaY(player.getYPos() + player.getCollidableArea().y);

                // get the object's collidable area position
                screen.setRewardCollidableAreaX(i, objects[i].getXPos() + objects[i].getCollidableArea().x);
                screen.setRewardCollidableAreaY(i, objects[i].getYPos() + objects[i].getCollidableArea().y);

                int cur;
                switch(player.getDirection()) {
                    case "up":
                        cur = player.getCollidableArea().y;
                        player.setCollidableAreaY(cur - player.getSpeed());
                        if(player.getCollidableArea().intersects(objects[i].getCollidableArea())) {
                            System.out.println("Up collision with reward");
                        }
                        break;
                    case "down":
                        cur = player.getCollidableArea().y;
                        player.setCollidableAreaY(cur + player.getSpeed());
                        if(player.getCollidableArea().intersects(objects[i].getCollidableArea())) {
                            System.out.println("Down collision with reward");
                        }
                        break;
                    case "right":
                        cur = player.getCollidableArea().x;
                        player.setCollidableAreaX(cur + player.getSpeed());
                        if(player.getCollidableArea().intersects(objects[i].getCollidableArea())) {
                            System.out.println("Right collision with reward");
                        }
                        break;
                    case "left":
                        cur = player.getCollidableArea().x;
                        player.setCollidableAreaX(cur - player.getSpeed());
                        if(player.getCollidableArea().intersects(objects[i].getCollidableArea())) {
                            System.out.println("Left collision with reward");
                        }
                        break;
                }

                // reset collidable area positions
                player.setCollidableAreaX(player.getCollidableAreaDefaultX());
                player.setCollidableAreaY(player.getCollidableAreaDefaultY());

                screen.setRewardCollidableAreaX(i, objects[i].getCollidableAreaDefaultX());
                screen.setRewardCollidableAreaY(i, objects[i].getCollidableAreaDefaultY());
            }
        }
        
        return index;
    }
}
