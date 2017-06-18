package com.binarymonks.jj.core.physics

import com.binarymonks.jj.core.scenes.Scene

class JJContactListener : com.badlogic.gdx.physics.box2d.ContactListener {

    /**
     * beginContact is the only hook that will guarantee that the objects have not been disposed.
     */
    override fun beginContact(contact: com.badlogic.gdx.physics.box2d.Contact) {
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

    private fun getResolver(fixture: com.badlogic.gdx.physics.box2d.Fixture): CollisionResolver {
        return checkNotNull((fixture.userData as com.binarymonks.jj.core.physics.PhysicsNode).physicsRoot).collisionResolver
    }

    private fun getThing(fixture: com.badlogic.gdx.physics.box2d.Fixture): Scene {
        val node = fixture.userData as com.binarymonks.jj.core.physics.PhysicsNode
        return checkNotNull(node.physicsRoot.parent)
    }


    /**
     * There may be several game loops before a contact ends. Objects may have been disposed by the beginContact.
     */
    override fun endContact(contact: com.badlogic.gdx.physics.box2d.Contact) {
        val fixtureA = contact.fixtureA
        val objectA = getThing(fixtureA)
        val resolverA = getResolver(fixtureA)

        val fixtureB = contact.fixtureB
        val objectB = getThing(fixtureB)
        val resolverB = getResolver(fixtureB)

        resolverA.endContact(objectB, fixtureB, contact, fixtureA)
        resolverB.endContact(objectA, fixtureA, contact, fixtureB)
    }

    override fun preSolve(contact: com.badlogic.gdx.physics.box2d.Contact, oldManifold: com.badlogic.gdx.physics.box2d.Manifold) {}

    override fun postSolve(contact: com.badlogic.gdx.physics.box2d.Contact, impulse: com.badlogic.gdx.physics.box2d.ContactImpulse) {}

}
