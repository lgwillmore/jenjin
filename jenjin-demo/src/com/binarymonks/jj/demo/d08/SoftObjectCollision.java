package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.ThingNode;

public class SoftObjectCollision extends CollisionFunction {

    float density = 1f;

    @Override
    public void collision(Thing me, Fixture myFixture, Thing other, Fixture otherFixture, Contact contact) {
        ThingNode node = (ThingNode) otherFixture.getUserData();
        if (node.hasProperty(Props.sharpness)) {
            Arrow arrow = other.getComponent(Arrow.class);
            if (!arrow.isEmbedded()) {
                other.physicsroot.getB2DBody().setAngularVelocity(0);
                float sharpness = node.getProperty(Props.sharpness);
                float penetrationDrag = Math.max(density - sharpness, 0);
                PenetrationTracker penetrationTracker = me.getComponent(PenetrationTracker.class);
                penetrationTracker.addPenetration(other, penetrationDrag);
            }
        } else {
            PenetrationTracker penetrationTracker = me.getComponent(PenetrationTracker.class);
            if (penetrationTracker.isPenetrating(other)) {
                penetrationTracker.addPenetration(other, 0);
            } else {
                //TODO: Make the sharp thing not sharp (remove property, change collision data)
            }
        }
    }

    @Override
    public CollisionFunction clone() {
        SoftObjectCollision clone = new SoftObjectCollision();
        clone.density = density;
        return clone;
    }
}
