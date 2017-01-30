package com.binarymonks.jj.render.specs;

/**
 * Created by lwillmore on 30/01/17.
 */
public class WidthHeight<T> {

    T parent;
    public float width;
    public float height;

    public WidthHeight(T parent) {
        this.parent = parent;
    }

    public T set(float width, float height) {
        this.width = width;
        this.height = height;
        return parent;
    }

}
