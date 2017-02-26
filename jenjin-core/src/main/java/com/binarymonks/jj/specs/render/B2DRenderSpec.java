package com.binarymonks.jj.specs.render;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.specs.NodeSpec;
import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.b2d.FixtureNodeSpec;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.render.nodes.PolygonRenderNode;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.render.nodes.ShapeRenderNode;
import com.binarymonks.jj.things.InstanceParams;

/**
 * This {@link RenderSpec} will attempt to render the box2d fixture from its parent {@link NodeSpec}
 */
public class B2DRenderSpec extends RenderSpec {

    @Override
    public RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec, InstanceParams instanceParams) {
        if (PolygonRenderNode.haveBuilt(this)) {
            return PolygonRenderNode.rebuild(this, instanceParams);
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
                        fixtureNodeSpec.rotationD,
                        instanceParams.scaleX,
                        instanceParams.scaleY
                );
            } else if (fixtureNodeSpec.shape instanceof B2DShapeSpec.Polygon) {
                B2DShapeSpec.Polygon polygon = (B2DShapeSpec.Polygon) fixtureNodeSpec.shape;
                return PolygonRenderNode.buildNew(
                        this,
                        polygon.edges,
                        N.ew(Vector2.class).set(fixtureNodeSpec.offsetX, fixtureNodeSpec.offsetY),
                        fixtureNodeSpec.rotationD,
                        instanceParams.scaleX,
                        instanceParams.scaleY);
            } else if (fixtureNodeSpec.shape instanceof B2DShapeSpec.Circle) {
                B2DShapeSpec.Circle circleSpec = (B2DShapeSpec.Circle) fixtureNodeSpec.shape;
                ShapeRenderNode.Circle circleNode = new ShapeRenderNode.Circle(this, true);
                circleNode.radius = circleSpec.radius * instanceParams.scaleX;
                circleNode.offset.set(fixtureNodeSpec.offsetX, fixtureNodeSpec.offsetY);
                return circleNode;
            } else if (fixtureNodeSpec.shape instanceof B2DShapeSpec.Chain) {
                B2DShapeSpec.Chain chainSpec = (B2DShapeSpec.Chain) fixtureNodeSpec.shape;
                ShapeRenderNode.ChainLine chainLineNode = new ShapeRenderNode.ChainLine(this, chainSpec.edges);
                return chainLineNode;
            }
        }
        return new RenderNode.Null(this);
    }
}