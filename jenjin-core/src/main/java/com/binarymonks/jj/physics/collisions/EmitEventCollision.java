package com.binarymonks.jj.physics.collisions;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.specs.PropField;
import com.binarymonks.jj.things.Thing;

public class EmitEventCollision extends CollisionFunction {

    public PropField<String, EmitEventCollision> message = new PropField<>(this);

    public EmitEventCollision(String message) {
        super();
        this.message.set(message);
    }

    public EmitEventCollision() {
        super();
    }


    @Override
    public void collision(Thing me, Fixture myFixture, Thing other, Fixture otherFixture, Contact contact) {
        JJ.events.send(message.get());
    }

    @Override
    public CollisionFunction clone() {
        EmitEventCollision clone = new EmitEventCollision();
        clone.message.copyFrom(message);
        return clone;
    }
}
