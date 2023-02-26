package org.GameObjects;

public class Punishment extends ImmovableObject {
    private int damage;

    public Punishment(int damage) {
        this.damage = damage;
    }
    public int getDamage() {
        return damage;
    }
}
