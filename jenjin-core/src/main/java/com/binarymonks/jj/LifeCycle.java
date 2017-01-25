package com.binarymonks.jj;

import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.lifecycle.LifeCycleAware;

public class LifeCycle {

    Array<LifeCycleAware> subsribers = new Array<>();

    LifeCycle(){}

    public void register(LifeCycleAware subscriber) {
        subsribers.add(subscriber);
    }

    public void deregister(LifeCycleAware subscriber) {
        subsribers.removeValue(subscriber, true);
    }

    void resize(int width, int height) {
        for (LifeCycleAware subcriber : subsribers) {
            subcriber.resize(width, height);
        }
    }

    void pause() {
        for (LifeCycleAware subcriber : subsribers) {
            subcriber.pause();
        }
    }

    void resume() {
        for (LifeCycleAware subcriber : subsribers) {
            subcriber.resume();
        }
    }

    void dispose() {
        for (LifeCycleAware subcriber : subsribers) {
            subcriber.dispose();
        }
    }


}
