package com.binarymonks.jj.render;

import com.binarymonks.jj.composable.Spatial;
import com.binarymonks.jj.render.composable.RenderOrder;
import com.binarymonks.jj.things.Thing;

public abstract class RenderNode {
    Thing parent;
    public RenderOrder order;
    public Spatial spatial;

    public abstract void render();
}
