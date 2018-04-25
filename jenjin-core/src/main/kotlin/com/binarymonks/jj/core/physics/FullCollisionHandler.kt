package com.binarymonks.jj.core.physics

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.Manifold
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.copy
import com.binarymonks.jj.core.scenes.Scene

/**
 * A single Collision handler to hook into full collision lifecycle.
 */
open class FullCollisionHandler {
    var ignoreProperties: Array<String> = Array()
    var matchProperties: Array<String> = Array()
    private var enabled = true

    private fun filterCollision(otherFixture: Fixture): Boolean {
        if (enabled) {
            val gameData = otherFixture.userData as PhysicsNode
            for (ignore in ignoreProperties) {
                if (gameData.hasProperty(ignore)) {
                    return true
                }
            }
            if (matchProperties.size > 0) {
                for (matchProp in matchProperties) {
                    if (gameData.hasProperty(matchProp)) {
                        return false
                    }
                }
            } else {
                return false
            }
        }
        return true
    }

    internal fun preSolveWrapper(me: Scene, myFixture: Fixture, other: Scene, otherFixture: Fixture, contact: Contact, oldManifold: Manifold): Boolean {
        if (filterCollision(otherFixture)) {
            return true
        }
        return preSolve(me, myFixture, other, otherFixture, contact, oldManifold)
    }

    internal fun beginWrapper(me: Scene, myFixture: Fixture, other: Scene, otherFixture: Fixture, contact: Contact): Boolean {
        if (filterCollision(otherFixture)) {
            return true
        }
        return beginCollision(me, myFixture, other, otherFixture, contact)
    }

    internal fun endWrapper(me: Scene, myFixture: Fixture, other: Scene, otherFixture: Fixture, contact: Contact): Boolean {
        if (filterCollision(otherFixture)) {
            return true
        }
        return end(me, myFixture, other, otherFixture, contact)
    }

    internal fun postSolveWrapper(me: Scene, myFixture: Fixture, other: Scene, otherFixture: Fixture, contact: Contact, impulse: ContactImpulse): Boolean {
        if (filterCollision(otherFixture)) {
            return true
        }
        return postSolve(me, myFixture, other, otherFixture, contact, impulse)
    }


    open fun preSolve(me: Scene, myFixture: Fixture, other: Scene, otherFixture: Fixture, contact: Contact, oldManifold: Manifold): Boolean{return false}
    open fun beginCollision(me: Scene, myFixture: Fixture, other: Scene, otherFixture: Fixture, contact: Contact): Boolean{return false}
    open fun end(me: Scene, myFixture: Fixture, other: Scene, otherFixture: Fixture, contact: Contact): Boolean{return false}
    open fun postSolve(me: Scene, myFixture: Fixture, other: Scene, otherFixture: Fixture, contact: Contact, impulse: ContactImpulse): Boolean{return false}

    open fun clone(): FullCollisionHandler {
        return copy(this)
    }

    fun disable() {
        enabled = false
    }

    fun enable() {
        enabled = true
    }

    open fun onAddToWorld() {

    }

    open fun onRemoveFromWorld() {


    }


}