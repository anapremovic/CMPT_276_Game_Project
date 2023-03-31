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
                // update collidable area positions for collision
                setCollidableAreaPositionsForCollision(player, i);

                int cur;
                switch(player.getDirection()) {
                    case "up":
                        cur = player.getCollidableArea().y;
                        player.setCollidableAreaY(cur - player.getSpeed());
                        break;
                    case "down":
                        cur = player.getCollidableArea().y;
                        player.setCollidableAreaY(cur + player.getSpeed());
                        break;
                    case "right":
                        cur = player.getCollidableArea().x;
                        player.setCollidableAreaX(cur + player.getSpeed());
                        break;
                    case "left":
                        cur = player.getCollidableArea().x;
                        player.setCollidableAreaX(cur - player.getSpeed());
                        break;
                }

                // if intersecting with an object, set collision of player to on
                if(player.getCollidableArea().intersects(objects[i].getCollidableArea())) {
                    if(objects[i].isSolid()) {
                        player.setCollisionOn(true); // COLLISION DETECTED
                    }
                    index = i;
                }

                // reset collidable area positions to default
                resetCollidableAreaPositions(player, i);
            }
        }
        
        return index;
    }

    /**
     * To detect a collision, we need to update the collidable area of each object in reference to where the object
     * is on the screen.
     * @param player    turtle to detect collision for
     * @param index     index of the immovable object to detect a collision for
     */
    private void setCollidableAreaPositionsForCollision(MainCharacter player, int index) {
        ImmovableObject[] objects = screen.getObjects();

        // get the player's collidable area position
        player.setCollidableAreaX(player.getXPos() + player.getCollidableArea().x);
        player.setCollidableAreaY(player.getYPos() + player.getCollidableArea().y);

        // get the object's collidable area position
        screen.setObjectCollidableAreaX(index, objects[index].getXPos() + objects[index].getCollidableArea().x);
        screen.setObjectCollidableAreaY(index, objects[index].getYPos() + objects[index].getCollidableArea().y);
    }

    /**
     * After detecting a collision, we want to reset the collidable area to the default values.
     *
     * @param player    turtle that experienced a collision
     * @param index     index of the immovable object that experienced a collision
     */
    private void resetCollidableAreaPositions(MainCharacter player, int index) {
        ImmovableObject[] objects = screen.getObjects();

        player.setCollidableAreaX(player.getCollidableAreaDefaultX());
        player.setCollidableAreaY(player.getCollidableAreaDefaultY());

        screen.setObjectCollidableAreaX(index, objects[index].getCollidableAreaDefaultX());
        screen.setObjectCollidableAreaY(index, objects[index].getCollidableAreaDefaultY());
    }
    public boolean detectEnemyCatchPlayer(MainCharacter player, int tileSize) {
        // check all enemies
        for (Enemy enemy : this.screen.getEnemies()) {
            if (Math.abs(enemy.getXPos() - player.getXPos()) < tileSize && Math.abs(enemy.getYPos() - player.getYPos())
                    < tileSize )
                return true;
        }
        return false;
    }
}
