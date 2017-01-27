package com.binarymonks.jj.layers;


import com.badlogic.gdx.utils.Array;

public class LayerStack implements Layer {

    Array<Layer> layers = new Array<>();


    @Override
    public void update() {
        for (Layer layer : layers) {
            layer.update();
        }
    }

    public void addLayerTop(Layer add) {
        layers.insert(layers.size, add);
    }

}
