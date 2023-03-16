package org.Logic;

import org.GameObjects.*;
import org.Display.*;

/**
 * Contains logic for checking and detecting collisions between player and tiles as well as player and immovable
 * objects.
 */
public class CollisionDetector {
    /**
     * The screen the collision takes place on.
     */
    Screen screen;

    /**
     * Object containing information on the tiles on which the collision takes place.
     */
    TileManager tileM;

    /**
     * Initialize this object's variables.
     *
     * @param screen    screen collisions occur on
     * @param tileM     tiles collisions occur on
     */
    public CollisionDetector(Screen screen, TileManager tileM) {
        this.screen = screen;
        this.tileM = tileM;
    }

    /**
     * Detect collision between the main character and any tiles they are currently touching.
     *
     * @param player    the main character
     * @return          the tile type the player is touching, corresponding to the indices of tileTypes[] in
     *                  TileManager
     */
    public int detectTile(MainCharacter player) {
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
        int tileType1 = 0;
        int tileType2 = 0;

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

        // check if player is touching an ocean tile (win condition)
        if(tileType1 == 3 || tileType2 == 3) {
            return 3;
        }
        return tileType1;
    }

    /**
     * Detect collision between the main character and a reward or a punishment.
     *
     * @param player    the main character
     * @return          the type of immovable object collided with, corresponding to indices in rewards[] in Screen
     */
    public int detectImmovableObject(MainCharacter player) {
        ImmovableObject[] objects = screen.getObjects();

        int index = 999; // index of object in rewards[] in Screen

        for(int i = 0; i < objects.length; i++) {
            if(objects[i] != null) {
                // get the player's collidable area position
                player.setCollidableAreaX(player.getXPos() + player.getCollidableArea().x);
                player.setCollidableAreaY(player.getYPos() + player.getCollidableArea().y);

                // get the object's collidable area position
                screen.setObjectCollidableAreaX(i, objects[i].getXPos() + objects[i].getCollidableArea().x);
                screen.setObjectCollidableAreaY(i, objects[i].getYPos() + objects[i].getCollidableArea().y);

                int cur;
                switch(player.getDirection()) {
                    case "up":
                        cur = player.getCollidableArea().y;
                        player.setCollidableAreaY(cur - player.getSpeed());
                        if(player.getCollidableArea().intersects(objects[i].getCollidableArea())) {
                            if(objects[i].isSolid()) {
                                player.setCollisionOn(true);
                            }
                            index = i;
                        }
                        break;
                    case "down":
                        cur = player.getCollidableArea().y;
                        player.setCollidableAreaY(cur + player.getSpeed());
                        if(player.getCollidableArea().intersects(objects[i].getCollidableArea())) {
                            if(objects[i].isSolid()) {
                                player.setCollisionOn(true);
                            }
                            index = i;
                        }
                        break;
                    case "right":
                        cur = player.getCollidableArea().x;
                        player.setCollidableAreaX(cur + player.getSpeed());
                        if(player.getCollidableArea().intersects(objects[i].getCollidableArea())) {
                            if(objects[i].isSolid()) {
                                player.setCollisionOn(true);
                            }
                            index = i;
                        }
                        break;
                    case "left":
                        cur = player.getCollidableArea().x;
                        player.setCollidableAreaX(cur - player.getSpeed());
                        if(player.getCollidableArea().intersects(objects[i].getCollidableArea())) {
                            if(objects[i].isSolid()) {
                                player.setCollisionOn(true);
                            }
                            index = i;
                        }
                        break;
                }

                // reset collidable area positions
                player.setCollidableAreaX(player.getCollidableAreaDefaultX());
                player.setCollidableAreaY(player.getCollidableAreaDefaultY());

                screen.setObjectCollidableAreaX(i, objects[i].getCollidableAreaDefaultX());
                screen.setObjectCollidableAreaY(i, objects[i].getCollidableAreaDefaultY());
            }
        }
        
        return index;
    }
}
