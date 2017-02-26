package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.components.Component;
import com.binarymonks.jj.components.Touchable;
import com.binarymonks.jj.things.InstanceParams;

public class QuiverTouch extends Touchable {

    Arrow arrow;

    @Override
    public void onTouchDown(Vector2 touchLocation) {
        arrow = JJ.things.createNow("arrow", InstanceParams.New().setPosition(touchLocation)).getComponent(Arrow.class);
    }

    @Override
    public void onTouchMove(Vector2 touchLocation) {
        arrow.updatePosition(touchLocation);
    }

    @Override
    public void onTouchUp() {
        arrow.release();
        arrow = null;
    }

    @Override
    public boolean trackTouchOffset() {
        return false;
    }

    @Override
    public Component clone() {
        return new QuiverTouch();
    }
}
