package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.ThingNode;

public class ArrowHeadCollisionFunction extends CollisionFunction {

    //TODO: Drag needs to be applied and tracked by the penetrated fixture....

    @Override
    public void collision(Thing me, Fixture myFixture, Thing other, Fixture otherFixture, Contact contact) {
    }

    @Override
    public CollisionFunction clone() {
        return null;
    }
}
