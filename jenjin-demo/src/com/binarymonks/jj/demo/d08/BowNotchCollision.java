package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.things.Thing;

public class BowNotchCollision extends CollisionFunction{

    @Override
    public void collision(Thing me, Fixture myFixture, Thing other, Fixture otherFixture, Contact contact) {
        Bow myBow = me.getComponent(Bow.class);
        Arrow arrow = other.getComponent(Arrow.class);
        myBow.notchArrow(arrow);
    }

    @Override
    public CollisionFunction clone() {
        return new BowNotchCollision();
    }
}
