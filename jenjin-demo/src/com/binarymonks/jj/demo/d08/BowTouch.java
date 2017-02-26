package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.components.Component;
import com.binarymonks.jj.components.Touchable;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;

public class BowTouch extends Touchable {


    Thing arrow;
    Vector2 touchOffset = N.ew(Vector2.class);

    @Override
    public void onTouchDown(Vector2 touchLocation) {
        Vector2 myPosition = parent.physicsroot.position();
        touchOffset.set(touchLocation).sub(myPosition);
        arrow = JJ.things.createNow("arrow", InstanceParams.New().setPosition(myPosition.x, myPosition.y));
    }

    @Override
    public void onTouchMove(Vector2 touchLocation) {
        Vector2 newArrowLocation = touchLocation.sub(touchOffset);
        arrow.physicsroot.setPosition(newArrowLocation.x, newArrowLocation.y);
    }

    @Override
    public void onTouchUp() {
        Vector2 trajectory = parent.physicsroot.position().sub(arrow.physicsroot.position());
        arrow.physicsroot.getB2DBody().applyLinearImpulse(trajectory.scl(100), Vector2.Zero, true);
        arrow=null;
    }

    @Override
    public boolean trackTouchOffset() {
        return false;
    }

    @Override
    public Component clone() {
        return new BowTouch();
    }
}
