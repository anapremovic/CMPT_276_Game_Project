package org.GameObjects;

public class BonusReward extends Reward {
    private double timeSinceSpawned;

    public int getPoints() {
        return points;
    }

    public double getTime() {
        return timeSinceSpawned;
    }
}
