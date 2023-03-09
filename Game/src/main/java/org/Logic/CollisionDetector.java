package org.Logic;

import org.GameObjects.ImmovableObject;
import org.GameObjects.MainCharacter;
import org.GameObjects.MovableObject;

public class CollisionDetector {
    MainCharacter object1;
    Object object2;

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
