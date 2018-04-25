package com.binarymonks.jj.core.physics

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.Manifold
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.scenes.Scene

class CollisionResolver {

    var me: Scene? = null
    var parent: CollisionResolver? = null
    val collisions = CollisionHandlers()
    var collisionCount = 0
        protected set

    fun preSolveContact(otherScene: Scene, otherFixture: Fixture, contact: Contact, myFixture: Fixture, oldManifold: Manifold) {
        for(full in collisions.full){
            if(full.preSolveWrapper(checkNotNull(me), myFixture, otherScene, otherFixture, contact, oldManifold)){
                return
            }
        }
        for (function in collisions.preSolves) {
            if (function.preSolveCollision(checkNotNull(me), myFixture, otherScene, otherFixture, contact, oldManifold)) {
                return
            }
        }
        if (parent != null) {
            parent!!.preSolveContact(otherScene, otherFixture, contact, myFixture, oldManifold)
        }
    }

    fun beginContact(otherObject: Scene, otherFixture: Fixture, contact: Contact, myFixture: Fixture) {
        collisionCount++
        for(full in collisions.full){
            if(full.beginWrapper(checkNotNull(me), myFixture, otherObject, otherFixture, contact)){
                return
            }
        }
        for (function in collisions.begins) {
            if (function.performCollision(checkNotNull(me), myFixture, otherObject, otherFixture, contact)) {
                return
            }
        }
        if (parent != null) {
            parent!!.beginContact(otherObject, otherFixture, contact, myFixture)
        }
    }

    fun endContact(otherObject: Scene, otherFixture: Fixture, contact: Contact, myFixture: Fixture) {
        for(full in collisions.full){
            if(full.endWrapper(checkNotNull(me), myFixture, otherObject, otherFixture, contact)){
                return
            }
        }
        for (function in collisions.ends) {
            if (function.performCollision(checkNotNull(me), myFixture, otherObject, otherFixture, contact)) {
                return
            }
        }
        if (parent != null) {
            parent!!.endContact(otherObject, otherFixture, contact, myFixture)
        }
    }

    fun postSolveContact(otherScene: Scene, otherFixture: Fixture, contact: Contact, myFixture: Fixture, impulse: ContactImpulse) {
        for(full in collisions.full){
            if(full.postSolveWrapper(checkNotNull(me), myFixture, otherScene, otherFixture, contact, impulse)){
                return
            }
        }
        for (function in collisions.postSolves) {
            if (function.postSolveCollision(checkNotNull(me), myFixture, otherScene, otherFixture, contact, impulse)) {
                return
            }
        }
        if (parent != null) {
            parent!!.postSolveContact(otherScene, otherFixture, contact, myFixture, impulse)
        }
    }

    fun addInitialBegin(collision: CollisionHandler) {
        collisions.begins.add(collision)
    }


    fun addEnd(collision: CollisionHandler) {
        collisions.ends.add(collision)
    }


    /**
     * Will disable every [CollisionHandler].
     */
    fun disableCurrentCollisions() {
        collisions.preSolves.forEach { it.disable() }
        collisions.begins.forEach { it.disable() }
        collisions.ends.forEach { it.disable() }
        collisions.postSolves.forEach { it.disable() }
        collisions.full.forEach { it.disable() }
    }


    /**
     * Will enable every [CollisionHandler].
     */
    fun enableCurrentCollisions() {
        collisions.preSolves.forEach { it.enable() }
        collisions.begins.forEach { it.enable() }
        collisions.ends.forEach { it.enable() }
        collisions.postSolves.forEach { it.enable() }
        collisions.full.forEach { it.enable() }
    }


}
