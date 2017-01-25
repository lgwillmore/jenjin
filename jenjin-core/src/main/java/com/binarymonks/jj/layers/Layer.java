package com.binarymonks.jj.layers;

import com.binarymonks.jj.lifecycle.LifeCycleAware;


public abstract class Layer implements LifeCycleAware {

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    abstract void update();
}
