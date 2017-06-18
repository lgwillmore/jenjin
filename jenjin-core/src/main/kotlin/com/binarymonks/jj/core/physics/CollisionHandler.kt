package com.binarymonks.jj.core.physics

import com.binarymonks.jj.core.scenes.Scene

abstract class CollisionHandler {

    protected var ignoreProperties = com.badlogic.gdx.utils.Array<String>()
    protected var matchProperties = com.badlogic.gdx.utils.Array<String>()
    private var enabled = true

    fun performCollision(me: Scene, myFixture: com.badlogic.gdx.physics.box2d.Fixture,
                         other: Scene, otherFixture: com.badlogic.gdx.physics.box2d.Fixture, contact: com.badlogic.gdx.physics.box2d.Contact) {
        if (enabled) {
            val gameData = otherFixture.userData as com.binarymonks.jj.core.physics.PhysicsNode
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

    abstract fun collision(me: Scene, myFixture: com.badlogic.gdx.physics.box2d.Fixture, other: Scene, otherFixture: com.badlogic.gdx.physics.box2d.Fixture, contact: com.badlogic.gdx.physics.box2d.Contact)

    abstract fun clone(): com.binarymonks.jj.core.physics.CollisionHandler

    fun copyProperties(copyFrom: com.binarymonks.jj.core.physics.CollisionHandler) {
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
