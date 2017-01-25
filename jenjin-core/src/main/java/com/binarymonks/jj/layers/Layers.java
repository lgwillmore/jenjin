package com.binarymonks.jj.layers;

public class Layers {

    LayersManager layersManager = new LayersManager();

    public void addLayerTop(Layer layer) {
        layersManager.addLayerTop(layer);
    }

    public void update() {
        layersManager.update();
    }

}
