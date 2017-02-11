package com.binarymonks.jj.render;

import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.render.nodes.RenderNode;

public class ThingLayer {
    public int layer;
    public Array<RenderNode> renderNodes = new Array<>();

    public ThingLayer(int layer) {
        this.layer = layer;
    }
}
