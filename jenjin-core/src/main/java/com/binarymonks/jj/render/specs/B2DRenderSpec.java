package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.physics.specs.PhysicsNodeSpec;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.render.nodes.PolygonRenderNode;
import com.binarymonks.jj.render.nodes.RenderNode;

/**
 * This {@link RenderSpec} will attempt to render the box2d fixture from its parent {@link com.binarymonks.jj.things.specs.NodeSpec}
 */
public class B2DRenderSpec extends RenderSpec<B2DRenderSpec> {

    @Override
    public RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec) {
        if (PolygonRenderNode.haveBuilt(this)) {
            return PolygonRenderNode.rebuild(this);
        }
        if (physicsNodeSpec instanceof FixtureNodeSpec) {
            FixtureNodeSpec fixtureNodeSpec = (FixtureNodeSpec) physicsNodeSpec;
            if (fixtureNodeSpec.shape instanceof B2DShapeSpec.PolygonRectangle) {
                B2DShapeSpec.PolygonRectangle rectangle = (B2DShapeSpec.PolygonRectangle) fixtureNodeSpec.shape;
                Array<Vector2> points = new Array<>();
                points.add(N.ew(Vector2.class).set(-rectangle.width / 2, -rectangle.height / 2));
                points.add(N.ew(Vector2.class).set(rectangle.width / 2, -rectangle.height / 2));
                points.add(N.ew(Vector2.class).set(rectangle.width / 2, rectangle.height / 2));
                points.add(N.ew(Vector2.class).set(-rectangle.width / 2, rectangle.height / 2));
                return PolygonRenderNode.buildNew(
                        this,
                        points,
                        N.ew(Vector2.class).set(fixtureNodeSpec.offsetX, fixtureNodeSpec.offsetY),
                        fixtureNodeSpec.rotationD);
            }
        }
        return null;
    }
}
