package com.binarymonks.jj.layers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.api.Layers;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.input.BaseInputProcessor;

public class LayerStack extends Layer implements Layers {

    Color clearColor = new Color(0, 0, 0, 1);
    Array<Layer> layers = new Array<>();
    GameRenderingLayer defaultGameLayer;

    public LayerStack(BaseInputProcessor baseInputProcessor) {
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(baseInputProcessor);
        defaultGameLayer=new GameRenderingLayer(
                Global.config.gameViewConfig.worldBoxWidth,
                Global.config.gameViewConfig.cameraPosX,
                Global.config.gameViewConfig.cameraPosY
        );
        addLayerTop(defaultGameLayer);
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
    public void addLayerTop(Layer layer) {
        layers.insert(layers.size, layer);
        layer.setStack(this);
        inputMultiplexer.addProcessor(0,layer.inputMultiplexer);
    }

    @Override
    public GameRenderingLayer getDefaultGameLayer() {
        return defaultGameLayer;
    }

    public void remove(Layer layer) {
        layers.removeValue(layer,true);
    }
}
