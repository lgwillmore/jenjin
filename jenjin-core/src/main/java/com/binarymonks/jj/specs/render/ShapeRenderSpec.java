package com.binarymonks.jj.specs.render;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.render.nodes.PolygonRenderNode;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.things.InstanceParams;

public abstract class ShapeRenderSpec extends SpatialRenderSpec {

    public boolean fill = true;


    public static class Rectangle extends ShapeRenderSpec {

        public float width;
        public float height;

        public Rectangle setDimension(float width, float height) {
            this.width = width;
            this.height = height;
            return this;
        }


        @Override
        public RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec, InstanceParams instanceParams) {
            if (PolygonRenderNode.haveBuilt(this)) {
                return PolygonRenderNode.rebuild(this, instanceParams);
            } else {
                Array<Vector2> points = new Array<>();
                points.add(N.ew(Vector2.class).set(-width / 2, -height / 2));
                points.add(N.ew(Vector2.class).set(width / 2, -height / 2));
                points.add(N.ew(Vector2.class).set(width / 2, height / 2));
                points.add(N.ew(Vector2.class).set(-width / 2, height / 2));
                return PolygonRenderNode.buildNew(
                        this,
                        points,
                        N.ew(Vector2.class).set(
                                spatial.getOffsetX(physicsNodeSpec),
                                spatial.getOffsetY(physicsNodeSpec)),
                        spatial.getRotationD(physicsNodeSpec),
                        instanceParams.scaleX,
                        instanceParams.scaleY
                );
            }
        }


    }


}
