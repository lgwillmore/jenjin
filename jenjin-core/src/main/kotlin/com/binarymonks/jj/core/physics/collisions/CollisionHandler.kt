package com.binarymonks.jj.core.physics.collisions

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.physics.PhysicsNode
import com.binarymonks.jj.core.things.Thing

abstract class CollisionHandler {

    protected var ignoreProperties = Array<String>()
    protected var matchProperties = Array<String>()
    private var enabled = true

    fun performCollision(me: Thing, myFixture: Fixture,
                         other: Thing, otherFixture: Fixture, contact: Contact) {
        if (enabled) {
            val gameData = otherFixture.userData as PhysicsNode
            for (ignore in ignoreProperties) {
                if (gameData.hasProperty(ignore)) {
                    return
                }
            }
            if (matchProperties.size > 0) {
                for (matchProp in matchProperties) {
                    if (gameData.hasProperty(matchProp)) {
                        collision(me, myFixture, other, otherFixture, contact)
                        break
                    }
                }
            } else {
                collision(me, myFixture, other, otherFixture, contact)
            }
        }
    }

    abstract fun collision(me: Thing, myFixture: Fixture, other: Thing, otherFixture: Fixture, contact: Contact)

    abstract fun clone(): CollisionHandler

    fun copyProperties(copyFrom: CollisionHandler) {
        this.matchProperties.addAll(copyFrom.matchProperties)
        this.ignoreProperties.addAll(copyFrom.ignoreProperties)
    }

    fun disable() {
        enabled = false
    }

    fun enable() {
        enabled = true
    }
}
