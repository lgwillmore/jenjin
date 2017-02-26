package com.binarymonks.jj.api;

import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.layers.Layer;

public interface Layers {
    void addLayerTop(Layer add);

    GameRenderingLayer getDefaultGameLayer();
}
