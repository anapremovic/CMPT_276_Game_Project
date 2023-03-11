package org.Logic;

import org.Display.*;
import org.GameObjects.*;

import java.util.ArrayList;

public class GameLogic {
    private boolean isRunning;
    private ArrayList<Barrier> barriers;
    private ArrayList<RegularReward> regularRewards;
    private BonusReward bonusReward;
    private MainCharacter player;
    private ArrayList<Enemy> enemies;
    private Screen screen;

    public GameLogic() {

    }

    public Screen getScreen() {
        return screen;
    }

    public void Render() {

    }
}
