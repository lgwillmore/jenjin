package com.binarymonks.jj.lifecycle;

public interface LifeCycleAware {

    void resize(int width, int height);

    void pause();

    void resume();

    void dispose();

}
