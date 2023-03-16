package org.GameObjects;

/**
 * Abstract class representing any immovable object on the screen that is a reward. In other words, that grants points
 * to the player when collected.
 */
public abstract class Reward extends ImmovableObject {

    /**
     * Number of points that this reward grants.
     */
    protected int points;

    // GETTERS

    public abstract int getPoints();
}
