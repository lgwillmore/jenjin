package com.binarymonks.jj.render.specs;

public class SpatialSpec<T> {

    public float offsetX;
    public float offsetY;
    public float rotationD;

    T parent;

    public SpatialSpec(T parent) {
        this.parent = parent;
    }

    public T setOffset(float x, float y) {
        this.offsetX = x;
        this.offsetY = y;
        return parent;
    }

    public T setRotationD(float rotationD) {
        this.rotationD = rotationD;
        return parent;
    }
}
