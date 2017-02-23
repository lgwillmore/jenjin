package com.binarymonks.jj.components;

import com.badlogic.gdx.math.Vector2;


/**
 * A {@link Touchable} that controls movement of the {@link com.binarymonks.jj.things.Thing} by setting the
 * velocity of the b2d body.
 */
public class VelocityTouchable extends Touchable {

    public float movementScale = 100;
    public boolean trackTouchOffset = true;

    @Override
    public void onTouchDown(Vector2 touchLocation) {

    }

    @Override
    public void onTouchMove(Vector2 touchLocation) {
        Vector2 currentPosition = parent.physicsroot.position();
        Vector2 direction = touchLocation.sub(currentPosition);
        parent.physicsroot.getB2DBody().setLinearVelocity(direction.scl(movementScale));
    }

    @Override
    public void onTouchUp() {
        parent.physicsroot.getB2DBody().setLinearVelocity(Vector2.Zero);
    }

    @Override
    public boolean trackTouchOffset() {
        return trackTouchOffset;
    }

    public VelocityTouchable setMovementScale(float movementForce) {
        this.movementScale = movementForce;
        return this;
    }


    public VelocityTouchable setTrackTouchOffset(boolean trackTouchOffset) {
        this.trackTouchOffset = trackTouchOffset;
        return this;
    }

    @Override
    public Component clone() {
        VelocityTouchable clone = new VelocityTouchable();
        clone.movementScale = movementScale;
        clone.trackTouchOffset=trackTouchOffset;
        return clone;
    }
}
