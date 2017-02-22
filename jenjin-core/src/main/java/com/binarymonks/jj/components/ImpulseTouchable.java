package com.binarymonks.jj.components;

import com.badlogic.gdx.math.Vector2;

public class ImpulseTouchable extends Touchable {


    @Override
    public void onTouchDown(Vector2 touchLocation) {

    }

    @Override
    public void onTouchMove(Vector2 touchLocation) {
        Vector2 currentPosition = parent.physicsroot.position();
        Vector2 direction = touchLocation.sub(currentPosition);
        ImpulseMovement movement = parent.getComponent(ImpulseMovement.class);
        movement.setImpulseVector(ImpulseMovement.Direction.New().set(direction, 1));
    }

    @Override
    public void onTouchUp() {
        parent.getComponent(ImpulseMovement.class).stop();
    }

    @Override
    public Component clone() {
        return new ImpulseTouchable();
    }
}
