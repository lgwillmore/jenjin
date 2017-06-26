package com.binarymonks.jj.core.physics

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Fixture
import com.binarymonks.jj.core.scenes.Scene

class JJContactListener : ContactListener {

    /**
     * beginContact is the only hook that will guarantee that the objects have not been disposed.
     */
    override fun beginContact(contact: Contact) {
        val fixtureA = contact.fixtureA
        val thingA = getScene(fixtureA)
        val resolverA = getResolver(fixtureA)

        val fixtureB = contact.fixtureB
        val thingB = getScene(fixtureB)
        val resolverB = getResolver(fixtureB)

        resolverA.beginContact(thingB, fixtureB, contact, fixtureA)
        resolverB.beginContact(thingA, fixtureA, contact, fixtureB)


        resolverA.finalBeginContact(thingB, fixtureB, contact, fixtureA)
        resolverB.finalBeginContact(thingA, fixtureA, contact, fixtureB)
    }

    private fun getResolver(fixture: Fixture): CollisionResolver {
        return (fixture.userData as PhysicsNode).collisionResolver
    }

    private fun getScene(fixture: Fixture): Scene {
        val node = fixture.userData as PhysicsNode
        return checkNotNull(node.physicsRoot.parent)
    }


    /**
     * There may be several game loops before a contact ends. Objects may have been disposed by the beginContact.
     */
    override fun endContact(contact: com.badlogic.gdx.physics.box2d.Contact) {
        val fixtureA = contact.fixtureA
        val objectA = getScene(fixtureA)
        val resolverA = getResolver(fixtureA)

        val fixtureB = contact.fixtureB
        val objectB = getScene(fixtureB)
        val resolverB = getResolver(fixtureB)

        resolverA.endContact(objectB, fixtureB, contact, fixtureA)
        resolverB.endContact(objectA, fixtureA, contact, fixtureB)
    }

    override fun preSolve(contact: com.badlogic.gdx.physics.box2d.Contact, oldManifold: com.badlogic.gdx.physics.box2d.Manifold) {}

    override fun postSolve(contact: com.badlogic.gdx.physics.box2d.Contact, impulse: com.badlogic.gdx.physics.box2d.ContactImpulse) {}

}
