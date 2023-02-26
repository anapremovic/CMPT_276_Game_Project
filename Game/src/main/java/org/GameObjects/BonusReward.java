package org.GameObjects;

public class BonusReward extends Reward {
    private double timeSinceSpawned;

    public BonusReward(int points) {
        this.points = points;
        timeSinceSpawned = 0;
    }

    public int getPoints() {
        return points;
    }

    public double getTime() {
        return timeSinceSpawned;
    }
}
