package com.binarymonks.jj.core.physics.collisions

import com.badlogic.gdx.physics.box2d.*
import com.binarymonks.jj.core.physics.PhysicsNode
import com.binarymonks.jj.core.things.Thing

class JJContactListener : ContactListener {

    /**
     * beginContact is the only hook that will guarantee that the objects have not been disposed.
     */
    override fun beginContact(contact: Contact) {
        val fixtureA = contact.fixtureA
        val thingA = getThing(fixtureA)
        val resolverA = getResolver(fixtureA)

        val fixtureB = contact.fixtureB
        val thingB = getThing(fixtureB)
        val resolverB = getResolver(fixtureB)

        resolverA.beginContact(thingB, fixtureB, contact, fixtureA)
        resolverB.beginContact(thingA, fixtureA, contact, fixtureB)


        resolverA.finalBeginContact(thingB, fixtureB, contact, fixtureA)
        resolverB.finalBeginContact(thingA, fixtureA, contact, fixtureB)
    }

    private fun getResolver(fixture: Fixture): CollisionResolver {
        return checkNotNull((fixture.userData as PhysicsNode).physicsRoot).collisionResolver
    }

    private fun getThing(fixture: Fixture): Thing {
        val node = fixture.userData as PhysicsNode
        return checkNotNull(node.physicsRoot.parent)
    }


    /**
     * There may be several game loops before a contact ends. Objects may have been disposed by the beginContact.
     */
    override fun endContact(contact: Contact) {
        val fixtureA = contact.fixtureA
        val objectA = getThing(fixtureA)
        val resolverA = getResolver(fixtureA)

        val fixtureB = contact.fixtureB
        val objectB = getThing(fixtureB)
        val resolverB = getResolver(fixtureB)

        resolverA.endContact(objectB, fixtureB, contact, fixtureA)
        resolverB.endContact(objectA, fixtureA, contact, fixtureB)
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {}

    override fun postSolve(contact: Contact, impulse: ContactImpulse) {}

}
