package org.Logic;

import org.GameObjects.*;
import org.Display.*;

public class CollisionDetector {

    Screen screen;
    MainCharacter object1;
    Object object2;

    public CollisionDetector(Screen screen) {
        this.screen = screen;
    }

    CollisionDetector(MainCharacter o1, ImmovableObject o2) {
        object1 = o1;
        object2 = o2;
    }

    CollisionDetector(MainCharacter o1, MovableObject o2) {
        object1 = o1;
        object2 = o2;
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
