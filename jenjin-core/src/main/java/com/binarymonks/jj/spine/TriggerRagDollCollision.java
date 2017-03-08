package com.binarymonks.jj.spine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.async.OneTimeTask;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Poolable;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.things.Thing;

public class TriggerRagDollCollision extends CollisionFunction {

    @Override
    public void collision(Thing me, Fixture myFixture, Thing other, Fixture otherFixture, Contact contact) {
        SpineBoneComponent boneComponent = me.getComponent(SpineBoneComponent.class);
        JJ.tasks.addPostPhysicsTask(N.ew(DelayedTriggerRagDoll.class).set(boneComponent,other));
    }

    @Override
    public CollisionFunction clone() {
        return new TriggerRagDollCollision();
    }

    public static class DelayedTriggerRagDoll extends OneTimeTask implements Poolable{
        SpineBoneComponent spineBone;
        Thing other;

        public DelayedTriggerRagDoll set(SpineBoneComponent spineBoneComponent, Thing other){
            this.spineBone = spineBoneComponent;
            this.other=other;
            return this;
        }

        @Override
        protected void doOnce() {
            spineBone.triggerRagDoll();
            Body otherBody = other.physicsroot.getB2DBody();
            Vector2 otherVelocity = otherBody.getLinearVelocity();
            float mass = otherBody.getMass();
            float momentum = otherVelocity.len()*mass;
            otherVelocity.nor().scl(momentum);
            spineBone.getParent().physicsroot.getB2DBody().applyForceToCenter(otherVelocity,true);
            Re.cycle(this);
        }

        @Override
        public void reset() {
            spineBone=null;
            other=null;
        }
    }
}
