package com.binarymonks.jj.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.binarymonks.jj.render.specs.RenderSpec;
import com.binarymonks.jj.things.Thing;

public abstract class RenderNode<SPEC extends RenderSpec> {
    public Thing parent;
    public static RenderNode<?> NULL = new Null(null);
    public SPEC spec;

    public RenderNode(SPEC spec) {
        this.spec = spec;
    }


    public abstract void render(OrthographicCamera camera);

    private static class Null extends RenderNode<RenderSpec.Null> {

        public Null(RenderSpec.Null spec) {
            super(spec);
        }

        @Override
        public void render(OrthographicCamera camera) {

        }
    }
}
