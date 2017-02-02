package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.render.PolygonRenderNode;
import com.binarymonks.jj.render.RenderNode;
import com.binarymonks.jj.render.ShapeRenderNode;

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
            PolygonSprite polygonSprite;
            if (!Global.renderWorld.polySpriteCache.containsKey(id)) {
                Array<Vector2> points = new Array<>();
                points.add(convert(new Vector2(0, 0)));
                points.add(convert(new Vector2(width, 0)));
                points.add(convert(new Vector2(width, height)));
                points.add(convert(new Vector2(0, height)));
                polygonSprite = Global.renderWorld.polygonSprite(id, points);
            } else {
                polygonSprite = Global.renderWorld.polySpriteCache.get(id);
            }

            return new PolygonRenderNode(this, polygonSprite);
        }

        private Vector2 convert(Vector2 vertex) {
            return vertex.add(offsetX-width/2, offsetY-height/2);
        }
    }


}
