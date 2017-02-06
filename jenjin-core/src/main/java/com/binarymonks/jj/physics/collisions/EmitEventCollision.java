package com.binarymonks.jj.physics.collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.things.Thing;

/**
 * Created by lwillmore on 06/02/17.
 */
public class EmitEventCollision extends CollisionFunction {

    String message;

    public EmitEventCollision(String message) {
        this.message = message;
    }

    public EmitEventCollision() {
    }

    @Override
    public void collision(Thing me, Fixture myFixture, Thing other, Fixture otherFixture, Contact contact) {
        JJ.events.send(message);
    }

    @Override
    public CollisionFunction clone() {
        EmitEventCollision clone = new EmitEventCollision();
        clone.message = message;
        return clone;
    }
}
