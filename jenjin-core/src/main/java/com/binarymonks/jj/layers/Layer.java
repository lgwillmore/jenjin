package com.binarymonks.jj.layers;


import com.badlogic.gdx.InputMultiplexer;

public abstract class Layer {

    protected InputMultiplexer inputMultiplexer = new InputMultiplexer();
    protected LayerStack stack;

    public abstract void update();

    public void setStack(LayerStack layerStack) {
        stack=layerStack;
    }

    public void removeSelf(){
        if(stack!=null){
            stack.remove(this);
        }
    }
}
