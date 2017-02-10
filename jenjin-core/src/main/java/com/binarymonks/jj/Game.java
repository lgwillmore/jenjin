package com.binarymonks.jj;

import com.badlogic.gdx.ApplicationListener;
import com.binarymonks.jj.backend.Global;


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

    JJConfig jjConfig = new JJConfig();

    public Game() {
    }

    public Game(JJConfig jjConfig) {
        this.jjConfig = jjConfig;
    }

    @Override
    public void create() {
        JJ.initialise(this.jjConfig);
        gameOn();
    }

    protected abstract void gameOn();

    @Override
    public void render() {
        Global.time.update();
        JJ.assets.update();
        Global.tasks.preloopTasks.update();
        Global.thingWorld.update();
        Global.layerStack.update();
        Global.physics.update();
        Global.tasks.postPhysicsTasks.update();
    }

    @Override
    public void resize(int width, int height) {
        Global.lifecycle.resize(width, height);
    }

    @Override
    public void pause() {
        Global.lifecycle.pause();
    }

    @Override
    public void resume() {
        Global.lifecycle.resume();
    }

    @Override
    public void dispose() {
        Global.lifecycle.dispose();
    }

}
