package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.components.Component;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Poolable;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.things.Thing;

public class PenetrationTracker extends Component {

    ObjectMap<Thing, Penetration> penetrations = new ObjectMap<>();
    Array<Thing> removals = new Array<>();
    int scheduleID;

    @Override
    public Component clone() {
        return new PenetrationTracker();
    }

    @Override
    public void doWork() {
        for (ObjectMap.Entry<Thing, Penetration> penEntry : penetrations) {
            if (penEntry.key.isMarkedForDestruction()) {
                removals.add(penEntry.key);
            }
        }
        clearRemovals();
    }

    @Override
    public void tearDown() {
        JJ.time.cancelScheduled(scheduleID);
    }


    @Override
    public void getReady() {
        scheduleID = JJ.time.scheduleInSeconds(this::applyDrags, 1 / 45f, true);
    }

    void applyDrags() {
        for (ObjectMap.Entry<Thing, Penetration> penEntry : penetrations) {
            if (penEntry.key.isMarkedForDestruction()) {
                removals.add(penEntry.key);
            } else {
                Body penetrator = penEntry.key.physicsroot.getB2DBody();
                Vector2 newVelocity = N.ew(Vector2.class)
                        .set(penetrator.getLinearVelocity())
                        .scl(1-penEntry.value.drag);
                Arrow arrow = penEntry.key.getComponent(Arrow.class);
                if(newVelocity.len()<0.001 && !arrow.isEmbedded()){
                    System.out.println("Welding");
                    Vector2 arrowPosition = penetrator.getPosition();
                    WeldJointDef weld = new WeldJointDef();
                    weld.initialize(parent.physicsroot.getB2DBody(),penEntry.key.physicsroot.getB2DBody(),arrowPosition);
                    Global.physics.world.createJoint(weld);
                    arrow.embed();
                }
                penetrator.setLinearVelocity(newVelocity);
            }
        }
        clearRemovals();
    }

    private void clearRemovals() {
        for (Thing thing : removals) {
            Penetration penetration = penetrations.remove(thing);
            Re.cycle(penetration);
        }
        removals.clear();
    }

    public void addPenetration(Thing other, float penetrationDrag) {
        if (penetrations.containsKey(other)) {
            penetrations.get(other).counter++;
        } else {
            penetrations.put(other, N.ew(Penetration.class).set(penetrationDrag));
        }
    }

    public void removePeneration(Thing other) {
        Penetration penetration = penetrations.get(other);
        penetration.counter--;
        if (penetration.counter <= 0) {
            penetrations.remove(other);
            Re.cycle(penetration);
        }
    }

    public boolean isPenetrating(Thing other) {
        return penetrations.containsKey(other);
    }

    public static class Penetration implements Poolable {
        int counter = 1;
        float drag;


        public Penetration set(float drag) {
            this.drag = drag;
            return this;
        }

        @Override
        public void reset() {
            counter = 1;
            drag = 0;
        }
    }
}
