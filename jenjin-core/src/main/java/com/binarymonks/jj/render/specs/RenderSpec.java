package com.binarymonks.jj.render.specs;


public abstract class RenderSpec {

    /****************************************
     *           Implementers Below         *
     ****************************************/

    public static class Null extends RenderSpec {

    }

    public static abstract class Shape extends RenderSpec {

        public static class Rect extends Shape {
            public SpatialSpec<Rect> spatial = new SpatialSpec<>(this);
            public DrawSpec<Rect> draw = new DrawSpec<>(this);
            public LayerSpec<Rect> layer = new LayerSpec<>(this);
            public WidthHeight<Rect> widthHeight = new WidthHeight<>(this);
        }

    }

}
