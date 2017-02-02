package com.binarymonks.jj.render.specs;


import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.render.RenderNode;

public abstract class RenderSpec<CONCRETE> {
    public int id = Global.renderWorld.nextRenderID();
    public int layer;
    public int thingPriority;
    public float offsetX;
    public float offsetY;
    public float rotationD;
    public Color color = new Color(0, 0, 0, 1);
    CONCRETE self;

    public CONCRETE setLayer(int layer) {
        this.layer = layer;
        return self;
    }

    public CONCRETE setPriority(int priority) {
        this.thingPriority = priority;
        return self;
    }

    public CONCRETE setOffset(float x, float y) {
        this.offsetX = x;
        this.offsetY = y;
        return self;
    }

    public CONCRETE setRotationD(float rotationD) {
        this.rotationD = rotationD;
        return self;
    }

    public CONCRETE setColor(Color color) {
        this.color.set(color);
        return self;
    }

    public abstract RenderNode<?> makeNode();

    public static class Null extends RenderSpec<Null> {

        @Override
        public RenderNode<?> makeNode() {
            return RenderNode.NULL;
        }
    }

}
