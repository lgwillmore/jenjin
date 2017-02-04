package com.binarymonks.jj.physics;

import com.badlogic.gdx.physics.box2d.*;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.ThingNode;

public class JJContactListener implements ContactListener {

    /**
     * beginContact is the only hook that will guarantee that the objects have not been disposed.
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Thing thingA = getThing(fixtureA);
        CollisionResolver resolverA = getResolver(fixtureA);

        Fixture fixtureB = contact.getFixtureB();
        Thing thingB = getThing(fixtureB);
        CollisionResolver resolverB = getResolver(fixtureB);

        resolverA.initialBeginContact(thingB, fixtureB, contact, fixtureA);
        resolverB.initialBeginContact(thingA, fixtureA, contact, fixtureB);


        resolverA.finalBeginContact(thingB, fixtureB, contact, fixtureA);
        resolverB.finalBeginContact(thingA, fixtureA, contact, fixtureB);
    }

    private CollisionResolver getResolver(Fixture fixture) {
        return ((ThingNode) fixture.getUserData()).getCollisionResolver();
    }

    private Thing getThing(Fixture fixture) {
        ThingNode node = (ThingNode) fixture.getUserData();
        return node.parent;
    }


    /**
     * There may be several game loops before a contact ends. Objects may have been disposed by the beginContact.
     */
    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Thing objectA = getThing(fixtureA);
        CollisionResolver resolverA = getResolver(fixtureA);

        Fixture fixtureB = contact.getFixtureB();
        Thing objectB = getThing(fixtureB);
        CollisionResolver resolverB = getResolver(fixtureB);

        resolverA.endContact(objectB, fixtureB, contact, fixtureA);
        resolverB.endContact(objectA, fixtureA, contact, fixtureB);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

}
