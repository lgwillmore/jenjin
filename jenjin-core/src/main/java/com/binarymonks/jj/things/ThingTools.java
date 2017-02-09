package com.binarymonks.jj.things;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.async.OneTimeTask;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.behaviour.Behaviour;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Poolable;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.render.RenderNode;
import com.binarymonks.jj.render.ThingLayer;

public class ThingTools {
    private static final String COLLISION_MASK_CACHE = "collision_mask_cache";
    private static final String COLLISION_CAT_CACHE = "collision_cat_cache";
    private static final short NONE = 0x0000;
    private static Array<Joint> jointsToDestroy = new Array<>();
    private static Vector2 physicsPoolLocation = new Vector2(10000, 10000);

    static void destroy(Thing thing) {
        if (thing.spec.pool) {
            thing.taskMaster.neutralise();
            neutralisePhysics(thing);
            Global.factories.things.recycle(thing);
        } else {
            destroyRender(thing);
            destroyPhysics(thing);
            thing.taskMaster.neutralise();
        }

    }

    private static void neutralisePhysics(Thing thing) {
        Body body = thing.physicsroot.getB2DBody();
        for (Fixture fixture : body.getFixtureList()) {
            ThingNode thingNode = (ThingNode) fixture.getUserData();
            thingNode.properties.put(COLLISION_CAT_CACHE, fixture.getFilterData().categoryBits);
            thingNode.properties.put(COLLISION_MASK_CACHE, fixture.getFilterData().maskBits);
            Filter filterData = fixture.getFilterData();
            filterData.categoryBits = NONE;
            filterData.maskBits = NONE;
            fixture.setFilterData(filterData);
        }
        jointsToDestroy.clear();
        for (JointEdge joint : body.getJointList()) {
            jointsToDestroy.add(joint.joint);
        }
        for (Joint joint : jointsToDestroy) {
            Global.physics.world.destroyJoint(joint);
        }
        jointsToDestroy.clear();
        body.setTransform(physicsPoolLocation, 0);
        body.setLinearVelocity(Vector2.Zero);
        body.setAngularVelocity(0);
        body.setActive(false);
        body.setAwake(false);
    }

    public static void resetPhysics(Thing thing, InstanceParams params) {
        Body body = thing.physicsroot.getB2DBody();
        for (Fixture fixture : body.getFixtureList()) {
            ThingNode fixtureGameData = (ThingNode) fixture.getUserData();
            Filter filterData = fixture.getFilterData();
            filterData.categoryBits = (short) fixtureGameData.properties.get(COLLISION_CAT_CACHE);
            filterData.maskBits = (short) fixtureGameData.properties.get(COLLISION_MASK_CACHE);
            fixture.setFilterData(filterData);
        }
        body.setTransform(params.x, params.y, params.rotationD * MathUtils.degreesToRadians);
        body.setActive(true);
        body.setAwake(true);
    }

    private static void destroyPhysics(Thing thing) {
        if (!Global.physics.isUpdating()) {
            Global.physics.world.destroyBody(thing.physicsroot.getB2DBody());
        } else {
            Global.tasks.addPostPhysicsTask(
                    N.ew(DestroyThingBody.class)
                            .setBody(thing.physicsroot.getB2DBody())
            );
        }
    }

    private static void destroyRender(Thing thing) {
        for (ObjectMap.Entry<Integer, ThingLayer> thingLayer : thing.renderRoot.thingLayers) {
            for (RenderNode node : thingLayer.value.renderNodes) {
                node.dispose();
            }
        }
    }

    public static class DestroyThingBody extends OneTimeTask implements Poolable {

        Body body;

        public DestroyThingBody setBody(Body body) {
            this.body = body;
            return this;
        }

        @Override
        protected void doOnce() {
            Global.physics.world.destroyBody(body);
            Re.cycle(this);
        }

        @Override
        public void reset() {
            body = null;
        }
    }
}
