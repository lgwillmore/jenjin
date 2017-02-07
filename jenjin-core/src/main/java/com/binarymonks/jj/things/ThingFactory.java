package com.binarymonks.jj.things;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.audio.SoundEffects;
import com.binarymonks.jj.audio.SoundParams;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.behaviour.Behaviour;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.physics.CollisionGroups;
import com.binarymonks.jj.physics.CollisionResolver;
import com.binarymonks.jj.physics.PhysicsRoot;
import com.binarymonks.jj.physics.specs.PhysicsRootSpec;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.render.RenderNode;
import com.binarymonks.jj.render.ThingLayer;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

public class ThingFactory {
    int idCounter = 0;

    public ThingFactory() {
        JJ.pools.registerManager(new Context.BuildContextPoolManager(), Context.class);
    }

    public Thing create(String thingSpecPath, InstanceParams instanceParams) {
        Thing thing = new Thing(thingSpecPath, idCounter++, instanceParams.uniqueInstanceName);
        Context context = N.ew(Context.class);
        context.thing = thing;
        context.thingSpec = Global.specs.specifications.get(thingSpecPath);
        context.instanceParams = instanceParams;

        setProperties(context);
        buildPhysicsRoot(context);
        buildNodes(context);
        wireInRenderNodes(context);
        buildBehaviour(context);
        buildSounds(context);

        Re.cycle(context);
        Global.thingWorld.add(thing);
        return thing;
    }

    private void setProperties(Context context) {
        for (ObjectMap.Entry<String, Object> prop : context.instanceParams.properties) {
            context.thing.properties.put(prop.key, prop.value);
        }
    }

    private void buildSounds(Context context) {
        SoundEffects soundEffects = new SoundEffects();
        for (SoundParams soundP : context.thingSpec.sounds) {
            soundEffects.addSoundEffect(soundP);
        }
        context.thing.sounds = soundEffects;
    }

    private void buildBehaviour(Context context) {
        for (Behaviour behaviour : context.thingSpec.behaviour) {
            Behaviour clone = behaviour.clone();
            context.thing.behaviour.add(clone);
            clone.setParent(context.thing);
        }
    }

    private void wireInRenderNodes(Context context) {
        ObjectMap<Integer, ThingLayer> thingLayers = new ObjectMap<>();
        for (ThingNode node : context.nodes) {
            if (!(node.render == RenderNode.NULL)) {
                int layer = node.render.spec.layer;
                if (layer < 0) {
                    throw new RuntimeException("You cannot have a layer less than 0");
                }
                if (!thingLayers.containsKey(layer)) {
                    thingLayers.put(layer, new ThingLayer(layer));
                }
                thingLayers.get(layer).renderNodes.add(node.render);
                node.render.setParent(context.thing);
            }
        }
        for (ObjectMap.Entry<Integer, ThingLayer> layers : thingLayers) {
            layers.value.renderNodes.sort(
                    (o1, o2) -> {
                        return o1.spec.thingPriority - o2.spec.thingPriority;
                    }
            );
        }
        context.thing.renderRoot.thingLayers = thingLayers;
        Global.renderWorld.addThing(context.thing);
    }

    private void buildNodes(Context context) {
        for (NodeSpec nodeSpec : context.thingSpec.nodes) {
            ThingNode node = new ThingNode(nodeSpec.name);

            buildFixture((FixtureNodeSpec) nodeSpec.physicsNodeSpec, node, context);

            RenderNode render = nodeSpec.renderSpec.makeNode(nodeSpec.physicsNodeSpec);
            node.render = render;
            context.nodes.add(node);

            if (node.name == null) {
                node.name = "ANON_NODE_" + context.thing.nodes.size;
            }
            context.thing.nodes.put(node.name, node);
            node.parent = context.thing;
        }
    }

    private Fixture buildFixture(FixtureNodeSpec nodeSpec, ThingNode node, Context context) {
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

        Fixture f = context.body.createFixture(fDef);
        node.fixture = f;
        f.setUserData(node);

        CollisionResolver resolver = new CollisionResolver();
        resolver.setSelf(context.thing);
        for (CollisionFunction ibegin : nodeSpec.initialBeginCollisions) {
            resolver.addInitialBegin(ibegin.clone());
        }
        for (CollisionFunction fbegin : nodeSpec.finalBeginCollisions) {
            resolver.addFinalBegin(fbegin.clone());
        }
        for (CollisionFunction end : nodeSpec.endCollisions) {
            resolver.addInitialBegin(end.clone());
        }

        node.collisionResolver = resolver;

        shape.dispose();
        return f;
    }

    private boolean hasCollisions(FixtureNodeSpec nodeSpec) {
        return (
                nodeSpec.initialBeginCollisions.size > 0
                        || nodeSpec.finalBeginCollisions.size > 0
                        || nodeSpec.endCollisions.size > 0
        );
    }

    private Shape buildShape(FixtureNodeSpec nodeSpec) {
        if (nodeSpec.shape instanceof B2DShapeSpec.PolygonRectangle) {
            B2DShapeSpec.PolygonRectangle polygonRectangle = (B2DShapeSpec.PolygonRectangle) nodeSpec.shape;
            PolygonShape boxshape = new PolygonShape();
            boxshape.setAsBox((polygonRectangle.width / 2.0f), (polygonRectangle.height / 2.0f), N.ew(Vector2.class).set(nodeSpec.offsetX, nodeSpec.offsetY), nodeSpec.rotationD * MathUtils.degreesToRadians);
            return boxshape;
        } else if (nodeSpec.shape instanceof B2DShapeSpec.Circle) {
            B2DShapeSpec.Circle circle = (B2DShapeSpec.Circle) nodeSpec.shape;
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(circle.radius);
            return circleShape;
        }
        return null;
    }

    private void buildPhysicsRoot(Context context) {
        PhysicsRootSpec.B2D bodyDef = (PhysicsRootSpec.B2D) context.thingSpec.physics;
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
        PhysicsRoot.B2DPhysicsRoot physicsRoot = new PhysicsRoot.B2DPhysicsRoot(context.body);
        context.thing.physicsroot = physicsRoot;
        context.body.setUserData(context.thing);
    }


    public static class Context {
        ThingSpec thingSpec;
        InstanceParams instanceParams;
        Thing thing;
        Body body;
        Array<ThingNode> nodes = new Array<>();

        public static class BuildContextPoolManager implements PoolManager<Context> {

            @Override
            public void reset(Context context) {
                context.thingSpec = null;
                Re.cycle(context.instanceParams);
                context.instanceParams = null;
                context.thing = null;
                context.body = null;
                context.nodes.clear();
            }

            @Override
            public Context create_new() {
                return new Context();
            }

            @Override
            public void dispose(Context context) {

            }
        }
    }
}
