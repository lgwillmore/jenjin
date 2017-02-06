package com.binarymonks.jj.demo.d01;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.things.Thing;

import static com.binarymonks.jj.demo.d01.D01_pong.BAT_HEIGHT;

/**
 * Created by lwillmore on 05/02/17.
 */
public class BatCollision extends CollisionFunction {

    float redirectionPower = 100;

    @Override
    public void collision(Thing me, Fixture myFixture, Thing other, Fixture otherFixture, Contact contact) {
        Vector2 myPosition = me.physicsroot.position();
        Vector2 collisionPosition = contact.getWorldManifold().getPoints()[0];

        float collisionOffset = (collisionPosition.y - myPosition.y) / (BAT_HEIGHT / 2);

        System.out.println(collisionOffset);

        other.physicsroot.getB2DBody().applyLinearImpulse(0,collisionOffset*redirectionPower,0,0,true);

    }

    @Override
    public CollisionFunction clone() {
        return new BatCollision();
    }
}
