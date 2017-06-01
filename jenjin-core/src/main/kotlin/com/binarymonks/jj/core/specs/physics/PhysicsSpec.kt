package com.binarymonks.jj.core.specs.physics

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.physics.CollisionHandler


class PhysicsSpec {
    var bodyType = BodyDef.BodyType.DynamicBody
    var linearDamping = 0f
    var angularDamping = 0f
    var gravityScale = 1f
    var bullet = false
    val fixedRotation: Boolean = false
    var allowSleep = true
    var beginCollisions: Array<CollisionHandler> = Array()
    var finalBeginCollisions: Array<CollisionHandler> = Array()
    var endCollisions: Array<CollisionHandler> = Array()
    var lights: Array<LightSpec> = Array()

    internal var fixtures: Array<FixtureSpec> = Array()

    fun addFixture(fixtureSpec: FixtureSpec) {
        fixtures.add(fixtureSpec)
    }

    /**
     * Adds another begin [CollisionHandler].
     * You can add as many as you want.
     *
     * Called when a collision begins
     */
    fun beginCollision(handler: CollisionHandler) {
        beginCollisions.add(handler)
    }

    /**
     * Adds another final begin [CollisionHandler].
     * You can add as many as you want.
     *
     * Called when a collision begins, but after the other objects begin collisions have been called.
     *
     * This is useful if this collision is going to destroy the other object for example.
     *
     * Called before/after the other objects final begin collisions.
     */
    fun finalBeginCollision(handler: CollisionHandler) {
        finalBeginCollisions.add(handler)
    }

    /**
     * Adds another end [CollisionHandler].
     * You can add as many as you want.
     *
     * Called when a collision ends
     */
    fun endCollision(handler: CollisionHandler) {
        endCollisions.add(handler)
    }
}