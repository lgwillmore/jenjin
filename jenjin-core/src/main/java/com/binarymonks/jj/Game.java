package com.binarymonks.jj;

import com.badlogic.gdx.ApplicationListener;


/**
 * This is the root thing to implement and provide to the libgdx frontend.
 * It is the engine for the application lifecycle and gameloop.
 * <p>
 * Do your initialisation stuff in {@link Game#gameOn()}.
 * <p>
 * If you want to hook into the application lifecycle close to the bone you can
 * do it here by overriding the relevant method - be sure to call super though :D
 * Alternatively - to save having to pass the hook down your object graph to where you want it
 * you can register a {@link com.binarymonks.jj.lifecycle.LifeCycleAware} at {@link JJ}
 */
public abstract class Game implements ApplicationListener {

    @Override
    public void create() {
        JJ.initialise();
        gameOn();
    }

    protected abstract void gameOn();

    @Override
    public void render() {
        JJ.time.update();
        JJ.render.update();
    }

    @Override
    public void resize(int width, int height) {
        JJ.lifecycle.resize(width, height);
    }

    @Override
    public void pause() {
        JJ.lifecycle.pause();
    }

    @Override
    public void resume() {
        JJ.lifecycle.resume();
    }

    @Override
    public void dispose() {
        JJ.lifecycle.dispose();
    }
}
