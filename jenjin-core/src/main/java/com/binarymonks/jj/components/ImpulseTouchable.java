package com.binarymonks.jj.components;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.utils.LinearAlgebra;

public class ImpulseTouchable extends Touchable {

    public float movementForce = 100;
    public float breakingForce = 10;
    public boolean trackTouchOffset = false;

    @Override
    public void onTouchDown(Vector2 touchLocation) {

    }

    @Override
    public void onTouchMove(Vector2 touchLocation) {
        Vector2 currentPosition = parent.physicsroot.position();
        Vector2 currentLinearVelocity = parent.physicsroot.getB2DBody().getLinearVelocity();
        Vector2 direction = touchLocation.sub(currentPosition);
        parent.physicsroot.getB2DBody().setLinearVelocity(direction.scl(movementForce));
//        float forceModifier = Math.min(direction.len(),1);
//        Vector2 oppositeDirection = N.ew(Vector2.class).set(direction).scl(-1);
//        direction.scl(movementForce*forceModifier);
//        float badComponentOfVelocity = LinearAlgebra.componentOfAinB(currentLinearVelocity, oppositeDirection);
////        if (badComponentOfVelocity > 0) {
////            direction.add(currentLinearVelocity.scl(-1));
////        }
//        ImpulseMovement movement = parent.getComponent(ImpulseMovement.class);
//        movement.setImpulseVector(direction.x, direction.y);
//        Re.cycle(oppositeDirection);
    }

    @Override
    public void onTouchUp() {
        parent.getComponent(ImpulseMovement.class).stop(breakingForce);
    }

    @Override
    public boolean trackTouchOffset() {
        return trackTouchOffset;
    }

    public ImpulseTouchable setMovementForce(float movementForce) {
        this.movementForce = movementForce;
        return this;
    }

    public ImpulseTouchable setBreakingForce(float breakingForce) {
        this.breakingForce = breakingForce;
        return this;
    }

    public ImpulseTouchable setTrackTouchOffset(boolean trackTouchOffset) {
        this.trackTouchOffset = trackTouchOffset;
        return this;
    }

    @Override
    public Component clone() {
        ImpulseTouchable clone = new ImpulseTouchable();
        clone.movementForce = movementForce;
        clone.breakingForce = breakingForce;
        clone.breakingForce = breakingForce;
        return clone;
    }
}
