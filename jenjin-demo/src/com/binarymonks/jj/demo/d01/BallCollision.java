package com.binarymonks.jj.demo.d01;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.things.Thing;

public class BallCollision extends CollisionFunction {
    @Override
    public void collision(Thing me, Fixture myFixture, Thing other, Fixture otherFixture, Contact contact) {
        System.out.println("Ping!");
    }

    @Override
    public CollisionFunction clone() {
        return new BallCollision();
    }
}
