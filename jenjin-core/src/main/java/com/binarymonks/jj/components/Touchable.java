package com.binarymonks.jj.components;

import com.badlogic.gdx.math.Vector2;

public abstract class Touchable extends Component {

    public abstract void onTouchDown(Vector2 touchLocation);

    public abstract void onTouchMove(Vector2 touchLocation);

    public abstract void onTouchUp();

    public abstract boolean trackTouchOffset();

    @Override
    public void doWork() {

    }

    @Override
    public void tearDown() {

    }

    @Override
    public void getReady() {

    }

    @Override
    public <T extends Component> Class<T> type() {
        return (Class<T>) Touchable.class;
    }
}
