package com.binarymonks.jj.spine;

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
        JJ.tasks.addPostPhysicsTask(N.ew(DelayedTriggerRagDoll.class).set(boneComponent));
    }

    @Override
    public CollisionFunction clone() {
        return new TriggerRagDollCollision();
    }

    public static class DelayedTriggerRagDoll extends OneTimeTask implements Poolable{
        SpineBoneComponent spineBone;

        public DelayedTriggerRagDoll set(SpineBoneComponent spineBoneComponent){
            this.spineBone = spineBoneComponent;
            return this;
        }

        @Override
        protected void doOnce() {
            spineBone.triggerRagDoll();
            Re.cycle(this);
        }

        @Override
        public void reset() {
            spineBone=null;
        }
    }
}
