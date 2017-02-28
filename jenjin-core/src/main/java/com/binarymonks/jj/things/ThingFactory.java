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
import com.binarymonks.jj.specs.SceneNodeSpec;
import com.binarymonks.jj.specs.SpecTools;
import com.binarymonks.jj.specs.ThingNodeSpec;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.specs.lights.LightSpec;
import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.b2d.FixtureNodeSpec;

public class ThingFactory {
    int idCounter = 0;
    int lightCounter = 0;
    ObjectMap<String, Array<Thing>> pooledThings = new ObjectMap<>();

    public ThingFactory() {
        JJ.pools.registerManager(new Context.BuildContextPoolManager(), Context.class);
    }

    public Thing create(ThingSpec thingSpec, InstanceParams instanceParams) {
            Context context = N.ew(Context.class);
            context.thingSpec = thingSpec;
            context.instanceParams = instanceParams;
            if (context.thingSpec.pool) {
                getPooled(thingSpec.getPath(), context);
            } else {
                buildNew(context);
            }
            setProperties(context);
            Global.renderWorld.addThing(context.thing);
            Global.thingWorld.add(context.thing);
            Thing thing = context.thing;
            Re.cycle(context);
            return thing;
    }

    private void getPooled(String thingSpecPath, Context context) {
        context.thing = checkPools(thingSpecPath);
        if (context.thing == null) {
            buildNew(context);
        } else {
            resetPooled(context);
        }
    }

    private Thing checkPools(String thingSpecPath) {
        if (pooledThings.containsKey(thingSpecPath) && pooledThings.get(thingSpecPath).size > 0) {
            return pooledThings.get(thingSpecPath).pop();
        }
        return null;
    }

    private void resetPooled(Context context) {
        ThingTools.resetPhysics(context.thing, context.instanceParams);
        context.thing.componentMaster.reactivate();
        for (ObjectMap.Entry<String, Light> light : context.thing.lights) {
            light.value.setActive(true);
        }
    }

    private void buildNew(Context context) {
        context.thing = new Thing(context.thingSpec.getPath(), idCounter++, context.instanceParams.uniqueInstanceName, context.thingSpec);
        buildPhysicsRoot(context);
        buildNodes(context);
        wireInRenderNodes(context);
        buildBehaviour(context);
        buildSounds(context);
        buildLights(context);
    }

    private void buildLights(Context context) {
        for (LightSpec lightSpec : context.thingSpec.lights) {
            addLight(lightSpec, context.thing, context.body, context.instanceParams.properties);
        }
    }

    private void addLight(LightSpec lightSpec, Thing thing, Body body, ObjectMap<String, Object> properties) {
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
        for (Component component : context.thingSpec.components) {
            Component clone = component.clone();
            context.thing.addComponent(clone);
            clone.setParent(context.thing);
        }
    }

    private void wireInRenderNodes(Context context) {
        ObjectMap<Integer, ThingLayer> defaultLayers = buildLayers(context, RenderWorld.DEFAULT_RENDER_GRAPH);
        ObjectMap<Integer, ThingLayer> lightSourceLayers = buildLayers(context, RenderWorld.LIGHTSOURCE_RENDER_GRAPH);
        context.thing.renderRoot.defaultThingLayers = defaultLayers;
        context.thing.renderRoot.lightSourceThingLayers = lightSourceLayers;
    }

    private ObjectMap<Integer, ThingLayer> buildLayers(Context context, String defaultRenderGraph) {
        ObjectMap<Integer, ThingLayer> thingLayers = new ObjectMap<>();
        for (ThingNode node : context.nodes) {
            if (!(node.render == RenderNode.NULL) && node.render.renderGraphName.equals(defaultRenderGraph)) {
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
                    (o1, o2) -> o1.spec.priority - o2.spec.priority
            );
        }
        return thingLayers;
    }

    private void buildNodes(Context context) {
        for (ThingNodeSpec nodeSpec : context.thingSpec.nodes) {
            ThingNode node = new ThingNode(nodeSpec.name);

            buildFixture(nodeSpec.physicsNodeSpec, node, context);

            RenderNode render = nodeSpec.renderSpec.makeNode(nodeSpec.physicsNodeSpec, context.instanceParams);
            node.render = render;
            context.nodes.add(node);

            if (node.name == null) {
                node.name = "ANON_NODE_" + context.thing.nodes.size;
            }
            context.thing.nodes.put(node.name, node);
            node.parent = context.thing;
        }
    }

    private void buildFixture(PhysicsNodeSpec nodeSpec, ThingNode node, Context context) {
        if (nodeSpec instanceof FixtureNodeSpec) {
            FixtureNodeSpec fixtureSpec = (FixtureNodeSpec) nodeSpec;
            Shape shape = buildShape(fixtureSpec, context.instanceParams);
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

            Fixture f = context.body.createFixture(fDef);
//            shape.dispose();
            node.fixture = f;
            f.setUserData(node);

            CollisionResolver resolver = new CollisionResolver();
            resolver.setSelf(context.thing);
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

    private boolean hasCollisions(FixtureNodeSpec nodeSpec) {
        return (
                nodeSpec.initialBeginCollisions.size > 0
                        || nodeSpec.finalBeginCollisions.size > 0
                        || nodeSpec.endCollisions.size > 0
        );
    }

    private Shape buildShape(FixtureNodeSpec nodeSpec, InstanceParams instanceParams) {
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
        def.gravityScale = bodyDef.gravityScale;
        context.body = Global.physics.world.createBody(def);
        PhysicsRoot.B2DPhysicsRoot physicsRoot = new PhysicsRoot.B2DPhysicsRoot(context.body);
        context.thing.physicsroot = physicsRoot;
        context.body.setUserData(context.thing);
    }

    public void recycle(Thing thing) {
        if (!pooledThings.containsKey(thing.path)) {
            pooledThings.put(thing.path, new Array<>());
        }
        pooledThings.get(thing.path).add(thing);
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

    private class NotAThingException extends RuntimeException {
        public NotAThingException(String thingSpecPath) {
            super(String.format("You cannot create a Thing from a non-Thing specification. Path=%s", thingSpecPath));
        }
    }
}
