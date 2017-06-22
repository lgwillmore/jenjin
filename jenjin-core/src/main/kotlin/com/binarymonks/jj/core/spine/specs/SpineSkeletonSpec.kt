package com.binarymonks.jj.core.spine.specs

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.physics.CollisionHandler
import com.binarymonks.jj.core.specs.physics.CollisionGroupSpec
import com.binarymonks.jj.core.specs.physics.CollisionGroupSpecExplicit
import com.binarymonks.jj.core.specs.physics.FixtureSpec


class SpineSkeletonSpec() {
    var boneWidth: Float = 0.1f
    var coreMass: Float = 0.5f
    var massFalloff = 0.8f
    var all = All()
    val boneFixtureOverrides: ObjectMap<String, FixtureSpec> = ObjectMap()

    constructor(build: SpineSkeletonSpec.() -> Unit) : this() {
        this.build()
    }

    fun overrideFixtureFor(boneName: String, build: FixtureSpec.() -> Unit) {
        val fixture = FixtureSpec()
        fixture.build()
        boneFixtureOverrides.put(boneName, fixture)
    }
}

class All{
    var collisionGroup: CollisionGroupSpec = CollisionGroupSpecExplicit()
    var restitution = 0.2f
    var friction= 0.2f
    var jointLowerLimitD = -30f
    var jointUpperLimitD = 90f
    var beginCollisions: Array<CollisionHandler> = Array()
    var finalBeginCollisions: Array<CollisionHandler> = Array()
    var endCollisions: Array<CollisionHandler> = Array()
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