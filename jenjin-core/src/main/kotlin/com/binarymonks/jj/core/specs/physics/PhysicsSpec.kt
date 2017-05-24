package com.binarymonks.jj.core.specs.physics

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.utils.Array


class PhysicsSpec {
    var bodyType = BodyDef.BodyType.DynamicBody
    var linearDamping = 0f
    var angularDamping = 0f
    var gravityScale = 1f
    var bullet = false
    val fixedRotation: Boolean = false
    var allowSleep = true

    internal var fixtures: Array<FixtureSpec> = Array()

    fun addFixture(fixtureSpec: FixtureSpec) {
        fixtures.add(fixtureSpec)
    }
}