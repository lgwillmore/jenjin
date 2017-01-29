package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.graphics.Color;

public interface RenderSpec {
    /****************************************
     *           Implementers Below         *
     ****************************************/

    class Null implements RenderSpec {

    }

    public class B2DShapeRenderSpec implements RenderSpec {

        Color color = Color.BLUE;

        public B2DShapeRenderSpec(Color color) {
            this.color = color;
        }

        public B2DShapeRenderSpec() {
        }
    }
}
