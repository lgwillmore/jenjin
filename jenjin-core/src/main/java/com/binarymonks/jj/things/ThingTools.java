package com.binarymonks.jj.things;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.async.OneTimeTask;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Poolable;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.render.RenderNode;
import com.binarymonks.jj.render.ThingLayer;

public class ThingTools {

    static void destroy(Thing thing) {
        destroyRender(thing);
        destroyPhysics(thing);
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
