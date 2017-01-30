package com.binarymonks.jj.render.specs;

/**
 * Created by lwillmore on 30/01/17.
 */
public class LayerSpec<T> {

    public int layer;
    public int thingPriority;
    T parent;

    public LayerSpec(T parent) {
        this.parent = parent;
    }

    public T setLayer(int layer) {
        this.layer = layer;
        return parent;
    }

    public T setPriority(int thingPriority) {
        this.thingPriority = thingPriority;
        return parent;
    }
}
