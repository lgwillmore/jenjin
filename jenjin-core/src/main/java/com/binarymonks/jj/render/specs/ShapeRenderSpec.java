package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.render.PolygonRenderNode;
import com.binarymonks.jj.render.RenderNode;

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
                Matrix3 trMatrix = N.ew(Matrix3.class);
                trMatrix.translate(offsetX, offsetY);
                trMatrix.rotate(rotationD);
                Array<Vector2> points = new Array<>();
                points.add(N.ew(Vector2.class).set(-width / 2, -height / 2).mul(trMatrix));
                points.add(N.ew(Vector2.class).set(width / 2, -height / 2).mul(trMatrix));
                points.add(N.ew(Vector2.class).set(width / 2, height / 2).mul(trMatrix));
                points.add(N.ew(Vector2.class).set(-width / 2, height / 2).mul(trMatrix));
                polygonSprite = Global.renderWorld.polygonSprite(id, points);
                Re.cycleItems(points);
                Re.cycle(trMatrix);
            } else {
                polygonSprite = Global.renderWorld.polySpriteCache.get(id);
            }
            return new PolygonRenderNode(this, polygonSprite);
        }


    }


}
