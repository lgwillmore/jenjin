package com.binarymonks.jj.lifecycle;

import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.api.LifeCycle;
import com.binarymonks.jj.lifecycle.LifeCycleAware;

public class LifeCyclePublisher implements LifeCycle {

    Array<LifeCycleAware> subsribers = new Array<>();

    public LifeCyclePublisher(){}

    @Override
    public void register(LifeCycleAware subscriber) {
        subsribers.add(subscriber);
    }

    @Override
    public void deregister(LifeCycleAware subscriber) {
        subsribers.removeValue(subscriber, true);
    }

    public void resize(int width, int height) {
        for (LifeCycleAware subcriber : subsribers) {
            subcriber.resize(width, height);
        }
    }

    public void pause() {
        for (LifeCycleAware subcriber : subsribers) {
            subcriber.pause();
        }
    }

    public void resume() {
        for (LifeCycleAware subcriber : subsribers) {
            subcriber.resume();
        }
    }

    public void dispose() {
        for (LifeCycleAware subcriber : subsribers) {
            subcriber.dispose();
        }
    }


}
