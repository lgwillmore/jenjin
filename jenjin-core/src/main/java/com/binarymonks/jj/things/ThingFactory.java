package com.binarymonks.jj.things;

import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.audio.SoundEffects;
import com.binarymonks.jj.audio.SoundParams;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.components.Component;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.physics.CollisionGroups;
import com.binarymonks.jj.physics.CollisionResolver;
import com.binarymonks.jj.physics.PhysicsRoot;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.render.RenderWorld;
import com.binarymonks.jj.render.ThingLayer;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.specs.SpecTools;
import com.binarymonks.jj.specs.ThingNodeSpec;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.specs.lights.LightSpec;
import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.b2d.FixtureNodeSpec;

public class ThingFactory<SPEC extends ThingSpec> {

    int lightCounter = 0;

    public Thing create(SPEC thingSpec, InstanceParams instanceParams) {
        Thing thing = null;
        if (thingSpec.pool) {
            thing = getPooled(thingSpec, instanceParams);
        } else {
            thing = buildNew(thingSpec, instanceParams);
        }
        wireIn(thing, instanceParams);
        return thing;
    }

    protected void wireIn(Thing thing, InstanceParams instanceParams) {
        setProperties(thing, instanceParams.properties);
        Global.renderWorld.addThing(thing);
        Global.thingWorld.add(thing);
    }

    protected Thing getPooled(SPEC thingSpec, InstanceParams instanceParams) {
        Thing thing = Global.factories.checkPools(thingSpec.getPath());
        if (thing == null) {
            return buildNew(thingSpec, instanceParams);
        } else {
            resetPooled(thing, instanceParams);
        }
        return thing;
    }

    protected void resetPooled(Thing thing, InstanceParams instanceParams) {
        ThingTools.resetPhysics(thing, instanceParams);
        thing.componentMaster.reactivate();
        for (ObjectMap.Entry<String, Light> light : thing.lights) {
            light.value.setActive(true);
        }
    }

    protected Thing buildNew(SPEC thingSpec, InstanceParams instanceParams) {
        Thing thing = new Thing(thingSpec.getPath(), Global.factories.nextID(), instanceParams.uniqueInstanceName, thingSpec);
        thing.setPool(thingSpec.pool);
        Body body = buildPhysicsRoot(thing, thingSpec, instanceParams);
        Array<ThingNode> nodes = buildNodes(thing, thingSpec, instanceParams, body);
        wireInRenderNodes(thing, nodes);
        buildBehaviour(thing, thingSpec);
        buildSounds(thing, thingSpec);
        buildLights(thing, thingSpec, instanceParams, body);
        return thing;
    }

    protected void buildLights(Thing thing, SPEC thingSpec, InstanceParams instanceParams, Body body) {
        for (LightSpec lightSpec : thingSpec.lights) {
            addLight(lightSpec, thing, body, instanceParams.properties);
        }
    }

    protected void addLight(LightSpec lightSpec, Thing thing, Body body, ObjectMap<String, Object> properties) {
        Light light = null;
        if (lightSpec instanceof LightSpec.Point) {
            LightSpec.Point pointSpec = (LightSpec.Point) lightSpec;
            light = new PointLight(Global.renderWorld.rayHandler, pointSpec.rays, SpecTools.freeze(pointSpec.color, properties), pointSpec.reach, pointSpec.offsetX, pointSpec.offsetY);
            light.attachToBody(body);
        }
        if (lightSpec.name == null) {
            lightSpec.name = "anon" + lightCounter++;
        }
        thing.lights.put(lightSpec.name, light);
    }

    protected void setProperties(Thing thing, ObjectMap<String, Object> instanceProps) {
        for (ObjectMap.Entry<String, Object> prop : instanceProps) {
            thing.properties.put(prop.key, prop.value);
        }
    }

    protected void buildSounds(Thing thing, SPEC thingSpec) {
        SoundEffects soundEffects = new SoundEffects();
        for (SoundParams soundP : thingSpec.sounds) {
            soundEffects.addSoundEffect(soundP);
        }
        thing.sounds = soundEffects;
    }

    protected void buildBehaviour(Thing thing, SPEC thingSpec) {
        for (Component component : thingSpec.components) {
            Component clone = component.clone();
            thing.addComponent(clone);
            clone.setParent(thing);
        }
    }

    protected void wireInRenderNodes(Thing thing, Array<ThingNode> nodes) {
        ObjectMap<Integer, ThingLayer> defaultLayers = buildLayers(thing, nodes, RenderWorld.DEFAULT_RENDER_GRAPH);
        ObjectMap<Integer, ThingLayer> lightSourceLayers = buildLayers(thing, nodes, RenderWorld.LIGHTSOURCE_RENDER_GRAPH);
        thing.renderRoot.defaultThingLayers = defaultLayers;
        thing.renderRoot.lightSourceThingLayers = lightSourceLayers;
    }

    protected ObjectMap<Integer, ThingLayer> buildLayers(Thing thing, Array<ThingNode> nodes, String renderGraph) {
        ObjectMap<Integer, ThingLayer> thingLayers = new ObjectMap<>();
        for (ThingNode node : nodes) {
            if (!(node.render == RenderNode.NULL) && node.render.renderGraphName.equals(renderGraph)) {
                int layer = node.render.spec.layer;
                if (layer < 0) {
                    throw new RuntimeException("You cannot have a layer less than 0");
                }
                if (!thingLayers.containsKey(layer)) {
                    thingLayers.put(layer, new ThingLayer(layer));
                }
                thingLayers.get(layer).renderNodes.add(node.render);
                node.render.setParent(thing);
            }
        }
        for (ObjectMap.Entry<Integer, ThingLayer> layers : thingLayers) {
            layers.value.renderNodes.sort(
                    (o1, o2) -> o1.spec.priority - o2.spec.priority
            );
        }
        return thingLayers;
    }

    protected Array<ThingNode> buildNodes(Thing thing, SPEC thingSpec, InstanceParams instanceParams, Body body) {
        Array<ThingNode> nodes = new Array<>();
        for (ThingNodeSpec nodeSpec : thingSpec.nodes) {
            ThingNode node = new ThingNode(nodeSpec.name);

            buildFixture(nodeSpec.physicsNodeSpec, thing, node, instanceParams, body);

            RenderNode render = nodeSpec.renderSpec.makeNode(nodeSpec.physicsNodeSpec, instanceParams);
            node.render = render;
            nodes.add(node);

            ThingTools.addNodeToThing(thing,node);
            node.properties.putAll(nodeSpec.properties);
        }
        return nodes;
    }

    protected void buildFixture(PhysicsNodeSpec nodeSpec, Thing thing, ThingNode node, InstanceParams instanceParams, Body body) {
        if (nodeSpec instanceof FixtureNodeSpec) {
            FixtureNodeSpec fixtureSpec = (FixtureNodeSpec) nodeSpec;
            Shape shape = buildShape(fixtureSpec, instanceParams);
            FixtureDef fDef = new FixtureDef();
            fDef.shape = shape;
            fDef.density = fixtureSpec.density;
            fDef.friction = fixtureSpec.friction;
            fDef.restitution = fixtureSpec.restitution;
            fDef.isSensor = fixtureSpec.isSensor;
            CollisionGroups.CollisionData cd = Global.physics.collisionGroups.getCollisionData(fixtureSpec.collisionData);
            fDef.filter.categoryBits = cd.category;
            fDef.filter.maskBits = cd.mask;
            Re.cycle(cd);

            Fixture f = body.createFixture(fDef);
            node.fixture = f;
            f.setUserData(node);

            CollisionResolver resolver = new CollisionResolver();
            resolver.setSelf(thing);
            for (CollisionFunction ibegin : fixtureSpec.initialBeginCollisions) {
                resolver.addInitialBegin(ibegin.clone());
            }
            for (CollisionFunction fbegin : fixtureSpec.finalBeginCollisions) {
                resolver.addFinalBegin(fbegin.clone());
            }
            for (CollisionFunction end : fixtureSpec.endCollisions) {
                resolver.addInitialBegin(end.clone());
            }

            node.collisionResolver = resolver;

            shape.dispose();
        }
    }

    protected Shape buildShape(FixtureNodeSpec nodeSpec, InstanceParams instanceParams) {
        if (nodeSpec.shape instanceof B2DShapeSpec.PolygonRectangle) {
            B2DShapeSpec.PolygonRectangle polygonRectangle = (B2DShapeSpec.PolygonRectangle) nodeSpec.shape;
            PolygonShape boxshape = new PolygonShape();
            boxshape.setAsBox((polygonRectangle.width * instanceParams.scaleX / 2.0f), (polygonRectangle.height * instanceParams.scaleY / 2.0f), N.ew(Vector2.class).set(nodeSpec.offsetX * instanceParams.scaleX, nodeSpec.offsetY * instanceParams.scaleY), nodeSpec.rotationD * MathUtils.degreesToRadians);
            return boxshape;
        } else if (nodeSpec.shape instanceof B2DShapeSpec.Circle) {
            B2DShapeSpec.Circle circle = (B2DShapeSpec.Circle) nodeSpec.shape;
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(circle.radius);
            circleShape.setPosition(N.ew(Vector2.class).set(nodeSpec.offsetX, nodeSpec.offsetY));
            return circleShape;
        } else if (nodeSpec.shape instanceof B2DShapeSpec.Polygon) {
            B2DShapeSpec.Polygon polygonSpec = (B2DShapeSpec.Polygon) nodeSpec.shape;
            PolygonShape polygonShape = new PolygonShape();
            Vector2[] vertices = new Vector2[polygonSpec.edges.size];
            for (int i = 0; i < polygonSpec.edges.size; i++) {
                vertices[i] = polygonSpec.edges.get(i);
            }
            polygonShape.set(vertices);
            return polygonShape;
        } else if (nodeSpec.shape instanceof B2DShapeSpec.Chain) {
            B2DShapeSpec.Chain chainSpec = (B2DShapeSpec.Chain) nodeSpec.shape;
            ChainShape chainShape = new ChainShape();
            Vector2[] vertices = new Vector2[chainSpec.edges.size];
            for (int i = 0; i < chainSpec.edges.size; i++) {
                vertices[i] = chainSpec.edges.get(i).add(nodeSpec.offsetX, nodeSpec.offsetY);
            }
            chainShape.createChain(vertices);
            return chainShape;
        }
        return null;
    }

    protected Body buildPhysicsRoot(Thing thing, SPEC thingSpec, InstanceParams instanceParams) {
        PhysicsRootSpec.B2D bodyDef = (PhysicsRootSpec.B2D) thingSpec.physics;
        BodyDef def = new BodyDef();
        def.position.set(instanceParams.x, instanceParams.y);
        def.angle = instanceParams.rotationD * MathUtils.degreesToRadians;
        def.type = bodyDef.bodyType;
        def.fixedRotation = bodyDef.fixedRotation;
        def.linearDamping = bodyDef.linearDamping;
        def.angularDamping = bodyDef.angularDamping;
        def.bullet = bodyDef.bullet;
        def.allowSleep = bodyDef.allowSleep;
        def.gravityScale = bodyDef.gravityScale;
        Body body = Global.physics.world.createBody(def);
        PhysicsRoot.B2DPhysicsRoot physicsRoot = new PhysicsRoot.B2DPhysicsRoot(body);
        thing.physicsroot = physicsRoot;
        body.setUserData(thing);
        return body;
    }
}
