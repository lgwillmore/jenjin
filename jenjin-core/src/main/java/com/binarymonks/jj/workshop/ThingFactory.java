package com.binarymonks.jj.workshop;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.physics.CollisionGroups;
import com.binarymonks.jj.physics.specs.PhysicsRootSpec;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

public class ThingFactory {

    public ThingFactory() {
        JJ.pools.registerManager(new Context.BuildContextPoolManager(), Context.class);
    }

    public Thing create(String thingSpecPath, InstanceParams instanceParams) {
        Thing thing = new Thing();
        Context context = N.ew(Context.class);
        context.thing = thing;
        context.thingSpec = Global.specs.specifications.get(thingSpecPath);
        context.instanceParams = instanceParams;

        buildPhysicsRoot(context);
        buildNodes(context);

        Re.cycle(context);
        return thing;
    }

    private void buildNodes(Context context) {
        for (NodeSpec nodeSpec : context.thingSpec.nodeSpecs) {
            Fixture fixture = buildFixture((FixtureNodeSpec)nodeSpec.physicsNodeSpec, context.body);
        }
    }

    private Fixture buildFixture(FixtureNodeSpec nodeSpec, Body body) {
        Shape shape = buildShape(nodeSpec);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = nodeSpec.density;
        fDef.friction = nodeSpec.friction;
        fDef.restitution = nodeSpec.restitution;
        fDef.isSensor = nodeSpec.isSensor;
        String collisionGroup = nodeSpec.collisionGroup;
        CollisionGroups.CollisionGroupData cd = Global.physics.collisionGroups.getGroupData(collisionGroup);
        fDef.filter.categoryBits = cd.category;
        fDef.filter.maskBits = cd.mask;

        Fixture f = body.createFixture(fDef);

        shape.dispose();
        return f;
    }

    private Shape buildShape(FixtureNodeSpec nodeSpec) {
        if (nodeSpec.shape instanceof B2DShapeSpec.PolygonSquare) {
            B2DShapeSpec.PolygonSquare polygonSquare = (B2DShapeSpec.PolygonSquare) nodeSpec.shape;
            PolygonShape boxshape = new PolygonShape();
            boxshape.setAsBox((polygonSquare.width / 2.0f), (polygonSquare.height / 2.0f), nodeSpec.offset, nodeSpec.rotationD * MathUtils.degreesToRadians);
            return boxshape;
        }
        return null;
    }

    private void buildPhysicsRoot(Context context) {
        PhysicsRootSpec.B2D bodyDef = (PhysicsRootSpec.B2D) context.thingSpec.physicsRootSpec;
        BodyDef def = new BodyDef();
        def.position.set(context.instanceParams.x, context.instanceParams.y);
        def.angle = context.instanceParams.rotationD * MathUtils.degreesToRadians;
        def.type = bodyDef.bodyType;
        def.fixedRotation = bodyDef.fixedRotation;
        def.linearDamping = bodyDef.linearDamping;
        def.angularDamping = bodyDef.angularDamping;
        def.bullet = bodyDef.bullet;
        def.allowSleep = bodyDef.allowSleep;
        context.body = Global.physics.world.createBody(def);
        context.body.setUserData(context.thing);
    }


    public static class Context {
        ThingSpec thingSpec;
        InstanceParams instanceParams;
        Thing thing;
        Body body;

        public static class BuildContextPoolManager implements PoolManager<Context> {

            @Override
            public void reset(Context context) {
                context.thingSpec = null;
                Re.cycle(context.instanceParams);
                context.instanceParams = null;
                context.thing = null;
                context.body = null;
            }

            @Override
            public Context create_new() {
                return new Context();
            }
        }
    }
}
