package com.binarymonks.jj.render.specs;


import com.binarymonks.jj.composable.Spatial;
import com.binarymonks.jj.composable.Dimension;
import com.binarymonks.jj.render.RenderNode;
import com.binarymonks.jj.render.ShapeRenderNode;
import com.binarymonks.jj.render.composable.Draw;
import com.binarymonks.jj.render.composable.RenderOrder;

public abstract class RenderSpec {
    public Spatial spatial = new Spatial();
    public RenderOrder order = new RenderOrder();

    /****************************************
     *           Implementers Below         *
     ****************************************/

    public static abstract class Shape extends RenderSpec {
        public Draw draw = new Draw();

        public static class Rect extends Shape {
            public Dimension dimension = new Dimension();
        }

    }

}
