package com.binarymonks.jj.components;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.utils.LinearAlgebra;

public class ImpulseTouchable extends Touchable {

    float force=100;

    @Override
    public void onTouchDown(Vector2 touchLocation) {

    }

    @Override
    public void onTouchMove(Vector2 touchLocation) {
        Vector2 currentPosition = parent.physicsroot.position();
        Vector2 currentLinearVelocity = parent.physicsroot.getB2DBody().getLinearVelocity();
        Vector2 direction = touchLocation.sub(currentPosition);
        Vector2 oppositeDirection = N.ew(Vector2.class).set(direction).scl(-1);
        float badComponentOfVelocity = LinearAlgebra.componentOfAinB(currentLinearVelocity,oppositeDirection);
        if(badComponentOfVelocity>0){
            direction.add(currentLinearVelocity.scl(-1));
        }
        else{
        }
        ImpulseMovement movement = parent.getComponent(ImpulseMovement.class);
        movement.setImpulseVector(direction.x*force,direction.y*force);
        Re.cycle(oppositeDirection);
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
