package org.Display;

import org.GameObjects.*;

public class ImmovableObjectDisplay {
    private Screen screen;

    public ImmovableObjectDisplay(Screen screen) {
        this.screen = screen;
    }

    public void displayObjects() {
        RegularReward carrot1 = new RegularReward(3 * screen.getTileSize(), 2 * screen.getTileSize());
        screen.setReward(0, carrot1);

        RegularReward carrot2 = new RegularReward(10 * screen.getTileSize(), 4 * screen.getTileSize());
        screen.setReward(1, carrot2);
    }
}
