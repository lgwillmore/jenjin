package com.binarymonks.jj.core.physics

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.scenes.Scene

class CollisionResolver {

    var parent: Scene? = null
    var begins = Array<CollisionHandler>()
    var finalBegins = Array<CollisionHandler>()
    var ends = Array<CollisionHandler>()
    var collisionCount = 0
        protected set

    fun beginContact(otherObject: Scene, otherFixture: Fixture, contact: Contact, myFixture: Fixture) {
        collisionCount++
        for (function in begins) {
            function.performCollision(checkNotNull(parent), myFixture, otherObject, otherFixture, contact)
        }
    }

    fun finalBeginContact(otherObject: Scene, otherFixture: Fixture, contact: Contact, myFixture: Fixture) {
        for (function in finalBegins) {
            function.performCollision(checkNotNull(parent), myFixture, otherObject, otherFixture, contact)
        }
    }

    fun endContact(otherObject: Scene, otherFixture: Fixture, contact: Contact, myFixture: Fixture) {
        for (function in ends) {
            function.performCollision(checkNotNull(parent), myFixture, otherObject, otherFixture, contact)
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
