package com.binarymonks.jj.layers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.lifecycle.LifeCycleAware;

public class LayersManager implements LifeCycleAware {

    Array<Layer> layers = new Array<>();

    LayersManager() {
        JJ.lifecycle.register(this);
    }

    @Override
    public void pause() {
        for (Layer layer : layers) {
            layer.pause();
        }
    }

    @Override
    public void resume() {
        for (Layer layer : layers) {
            layer.resume();
        }
    }

    @Override
    public void dispose() {
        for (Layer layer : layers) {
            layer.dispose();
        }
    }

    public void update() {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (Layer layer : layers) {
            layer.update();
        }
    }

    public void addLayerTop(Layer add) {
        layers.insert(layers.size, add);
    }

    @Override
    public void resize(int width, int height) {
        for (Layer layer : layers) {
            layer.resize(width, height);
        }
    }
}
