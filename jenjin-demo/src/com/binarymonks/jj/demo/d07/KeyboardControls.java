package com.binarymonks.jj.demo.d07;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.components.ForceMovement;
import com.binarymonks.jj.input.Actions;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Re;

public class KeyboardControls {

    ForceMovement forceMovement;
    boolean up = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;

    public KeyboardControls(ForceMovement forceMovement) {
        this.forceMovement = forceMovement;
    }

    public boolean up(Actions.Key keyAction) {
        up = keyAction.equals(Actions.Key.PRESSED);
        updateSteer();
        return true;
    }


    public boolean down(Actions.Key keyAction) {
        down = keyAction.equals(Actions.Key.PRESSED);
        updateSteer();
        return true;
    }

    public boolean left(Actions.Key keyAction) {
        left = keyAction.equals(Actions.Key.PRESSED);
        updateSteer();
        return true;
    }

    public boolean right(Actions.Key keyAction) {
        right = keyAction.equals(Actions.Key.PRESSED);
        updateSteer();
        return true;
    }

    private void updateSteer() {
        float y = 0;
        float x = 0;
        if (up) y++;
        if (down) y--;
        if (right) x++;
        if (left) x--;
        Vector2 direction = N.ew(Vector2.class).set(x, y);
        if (direction.equals(Vector2.Zero)) {
            forceMovement.stop();
        } else {
            forceMovement.setSteer(ForceMovement.Steer.New().set(direction, 1));
        }
        Re.cycle(direction);
    }

}
