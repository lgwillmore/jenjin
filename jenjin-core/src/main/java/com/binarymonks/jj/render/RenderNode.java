package com.binarymonks.jj.render;

import com.binarymonks.jj.render.specs.RenderSpec;
import com.binarymonks.jj.things.Thing;

public abstract class RenderNode<SPEC extends RenderSpec> {
    Thing parent;
    public SPEC spec;

    public RenderNode(SPEC spec) {
        this.spec = spec;
    }


    public abstract void render();

    public static class Null extends RenderNode<RenderSpec.Null> {

        public Null(RenderSpec.Null spec) {
            super(spec);
        }

        @Override
        public void render() {

        }
    }
}
