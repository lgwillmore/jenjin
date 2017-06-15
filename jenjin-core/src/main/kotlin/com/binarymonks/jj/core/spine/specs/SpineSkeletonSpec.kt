package com.binarymonks.jj.core.spine.specs

import com.binarymonks.jj.core.specs.physics.CollisionGroupSpec
import com.binarymonks.jj.core.specs.physics.CollisionGroupSpecExplicit


class SpineSkeletonSpec() {
    var boneWidth: Float = 0.1f
    var collisionGroup: CollisionGroupSpec = CollisionGroupSpecExplicit()
    var coreMass: Float = 0.5f
    var massFalloff = 0.8f


    constructor(build: SpineSkeletonSpec.() -> Unit) : this() {
        this.build()
    }
}