package com.binarymonks.jj;

import com.binarymonks.jj.layers.Layers;

public class Render {
    public Layers layers = new Layers();

    Render() {
    }

    void update() {
        layers.update();
    }

}
