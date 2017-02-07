package com.binarymonks.jj.physics.collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.things.Thing;

public class DestroyCollision extends CollisionFunction {

    boolean destroyMe;
    boolean destroyOther;

    public DestroyCollision(boolean destroyMe, boolean destroyOther) {
        this.destroyMe = destroyMe;
        this.destroyOther = destroyOther;
    }

    @Override
    public void collision(Thing me, Fixture myFixture, Thing other, Fixture otherFixture, Contact contact) {
        if (destroyMe) {
            me.markForDestruction();
        }
        if (destroyOther) {
            other.markForDestruction();
        }
    }

    @Override
    public CollisionFunction clone() {
        return new DestroyCollision(destroyMe, destroyOther);
    }
}
