package com.binarymonks.jj.render.specs;

import com.binarymonks.jj.render.RenderNode;
import com.binarymonks.jj.render.ShapeRenderNode;
import javafx.scene.shape.Shape;

public abstract class ShapeRenderSpec<CONCRETE> extends RenderSpec<CONCRETE> {

    public boolean fill = true;

    public CONCRETE setFill(boolean fill) {
        this.fill = fill;
        return self;
    }

    public static class Rectangle extends ShapeRenderSpec<Rectangle> {

        public float width;
        public float height;

        public Rectangle() {
            this.self = this;
        }

        public Rectangle setDimension(float width, float height) {
            this.width = width;
            this.height = height;
            return this;
        }


        @Override
        public RenderNode<?> makeNode() {
            return new ShapeRenderNode.RectangleNode(this);
        }
    }


}
