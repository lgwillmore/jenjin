package com.binarymonks.jj.core.physics

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.scenes.Scene

class CollisionResolver {

    var me: Scene? = null
    var parent: CollisionResolver? = null
    var begins = Array<CollisionHandler>()
    var finalBegins = Array<CollisionHandler>()
    var ends = Array<CollisionHandler>()
    var collisionCount = 0
        protected set

    fun beginContact(otherObject: Scene, otherFixture: Fixture, contact: Contact, myFixture: Fixture) {
        collisionCount++
        var propogate = true
        for (function in begins) {
            if (function.performCollision(checkNotNull(me), myFixture, otherObject, otherFixture, contact)) {
                propogate = false
                break
            }
        }
        if (propogate && parent != null) {
            parent!!.beginContact(otherObject, otherFixture, contact, myFixture)
        }
    }

    fun finalBeginContact(otherObject: Scene, otherFixture: Fixture, contact: Contact, myFixture: Fixture) {
        var propogate = true
        for (function in finalBegins) {
            if (function.performCollision(checkNotNull(me), myFixture, otherObject, otherFixture, contact)) {
                propogate = false
                break
            }
        }
        if (propogate && parent != null) {
            parent!!.finalBeginContact(otherObject, otherFixture, contact, myFixture)
        }
    }

    fun endContact(otherObject: Scene, otherFixture: Fixture, contact: Contact, myFixture: Fixture) {
        var propogate = true
        for (function in ends) {
            if (function.performCollision(checkNotNull(me), myFixture, otherObject, otherFixture, contact)) {
                propogate = false
                break
            }
        }
        if (propogate && parent != null) {
            parent!!.endContact(otherObject, otherFixture, contact, myFixture)
        }
    }

    fun addInitialBegin(collision: CollisionHandler) {
        begins.add(collision)
    }

    fun addFinalBegin(collision: CollisionHandler) {
        finalBegins.add(collision)
    }

    fun addEnd(collision: CollisionHandler) {
        ends.add(collision)
    }


    /**
     * Will disable every [CollisionHandler].
     */
    fun disableCurrentCollisions() {
        for (CollisionHandler in ends) {
            CollisionHandler.disable()
        }
        for (CollisionHandler in finalBegins) {
            CollisionHandler.disable()
        }
        for (CollisionHandler in begins) {
            CollisionHandler.disable()
        }
    }


    /**
     * Will enable every [CollisionHandler].
     */
    fun enableCurrentCollisions() {
        for (CollisionHandler in ends) {
            CollisionHandler.enable()
        }
        for (CollisionHandler in finalBegins) {
            CollisionHandler.enable()
        }
        for (CollisionHandler in begins) {
            CollisionHandler.enable()
        }
    }

    fun removeAllInitialBegin(collisions: Array<CollisionHandler>) {
        begins.removeAll(collisions, true)
    }


}
