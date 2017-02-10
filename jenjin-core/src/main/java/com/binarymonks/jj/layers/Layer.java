package com.binarymonks.jj.layers;


import com.badlogic.gdx.InputMultiplexer;

public abstract class Layer {

    protected InputMultiplexer inputMultiplexer = new InputMultiplexer();

    public abstract void update();
}
