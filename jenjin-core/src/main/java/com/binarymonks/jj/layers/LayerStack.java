package com.binarymonks.jj.layers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.api.Layers;
import com.binarymonks.jj.input.BaseInputProcessor;

public class LayerStack extends Layer implements Layers {

    Color clearColor = new Color(0, 0, 0, 1);
    Array<Layer> layers = new Array<>();

    public LayerStack(BaseInputProcessor baseInputProcessor) {
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(baseInputProcessor);
    }

    @Override
    public void update() {
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (Layer layer : layers) {
            layer.update();
        }
    }

    @Override
    public void addLayerTop(Layer add) {
        layers.insert(layers.size, add);
        inputMultiplexer.addProcessor(0,add.inputMultiplexer);
    }

}
