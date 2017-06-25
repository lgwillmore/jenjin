package com.binarymonks.jj.core.physics

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import com.binarymonks.jj.core.copy
import com.binarymonks.jj.core.scenes.Scene

abstract class CollisionHandler {

    protected var ignoreProperties: Array<String> = Array()
    protected var matchProperties: Array<String> = Array()
    private var enabled = true

    fun performCollision(me: Scene, myFixture: Fixture,
                         other: Scene, otherFixture: Fixture, contact: Contact) {
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

    abstract fun collision(me: Scene, myFixture: Fixture, other: Scene, otherFixture: Fixture, contact: Contact)

    open fun clone(): CollisionHandler{
        return copy(this)
    }

    fun disable() {
        enabled = false
    }

    fun enable() {
        enabled = true
    }
}
