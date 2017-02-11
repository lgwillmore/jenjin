package com.binarymonks.jj.render.nodes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.binarymonks.jj.render.specs.RenderSpec;
import com.binarymonks.jj.specs.PropField;
import com.binarymonks.jj.things.Thing;

public abstract class RenderNode<SPEC extends RenderSpec> {
    protected Thing parent;
    public static RenderNode<?> NULL = new Null(new RenderSpec.Null());
    public SPEC spec;
    public PropField<Color, ?> color = new PropField<>(new Color(0, 0, 0, 1));

    public RenderNode(SPEC spec) {
        this.spec = spec;
        this.color.copyFrom(spec.color);
    }


    public abstract void render(OrthographicCamera camera);

    public abstract void dispose();

    private static class Null extends RenderNode<RenderSpec.Null> {


        public Null(RenderSpec.Null spec) {
            super(spec);
        }

        @Override
        public void render(OrthographicCamera camera) {

        }

        @Override
        public void dispose() {

        }
    }

    public void setParent(Thing parent) {
        this.parent = parent;
        color.setParent(parent);
    }

}
