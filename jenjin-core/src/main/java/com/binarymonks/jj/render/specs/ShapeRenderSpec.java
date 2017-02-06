package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.physics.specs.PhysicsNodeSpec;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.render.PolygonRenderNode;
import com.binarymonks.jj.render.RenderNode;

public abstract class ShapeRenderSpec<CONCRETE extends ShapeRenderSpec> extends SpatialRenderSpec<CONCRETE> {

    public boolean fill = true;

    public CONCRETE setFill(boolean fill) {
        this.fill = fill;
        return self;
    }


    public static class Rectangle extends ShapeRenderSpec<Rectangle> {

        public float width;
        public float height;

        public Rectangle setDimension(float width, float height) {
            this.width = width;
            this.height = height;
            return this;
        }


        @Override
        public RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec) {
            if (PolygonRenderNode.haveBuilt(this)) {
                return PolygonRenderNode.rebuild(this);
            } else {
                Array<Vector2> points = new Array<>();
                points.add(N.ew(Vector2.class).set(-width / 2, -height / 2));
                points.add(N.ew(Vector2.class).set(width / 2, -height / 2));
                points.add(N.ew(Vector2.class).set(width / 2, height / 2));
                points.add(N.ew(Vector2.class).set(-width / 2, height / 2));
                return PolygonRenderNode.buildNew(this, points, N.ew(Vector2.class).set(offsetX, offsetY), rotationD);
            }
        }


    }


}
