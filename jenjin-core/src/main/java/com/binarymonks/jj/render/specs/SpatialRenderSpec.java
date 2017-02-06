package com.binarymonks.jj.render.specs;


public abstract class SpatialRenderSpec<CONCRETE extends SpatialRenderSpec> extends RenderSpec<CONCRETE> {
    public float offsetX;
    public float offsetY;
    public float rotationD;


    public CONCRETE setOffset(float x, float y) {
        this.offsetX = x;
        this.offsetY = y;
        return self;
    }

    public CONCRETE setRotationD(float rotationD) {
        this.rotationD = rotationD;
        return self;
    }

}
