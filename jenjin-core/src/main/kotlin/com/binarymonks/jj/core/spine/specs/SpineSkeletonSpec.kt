package com.binarymonks.jj.core.spine.specs

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.physics.CollisionHandlers
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

class All {
    var collisionGroup: CollisionGroupSpec = CollisionGroupSpecExplicit()
    var restitution = 0.2f
    var friction = 0.2f
    val collisions = CollisionHandlers()
}