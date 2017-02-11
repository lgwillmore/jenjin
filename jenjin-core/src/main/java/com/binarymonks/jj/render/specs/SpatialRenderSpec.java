package com.binarymonks.jj.render.specs;


public abstract class SpatialRenderSpec<CONCRETE extends SpatialRenderSpec> extends RenderSpec<CONCRETE> {
    public Spatial spatial = new Spatial.Fixed();
    public CONCRETE setSpatial(Spatial spatial) {
        this.spatial = spatial;
        return self;
    }
}
