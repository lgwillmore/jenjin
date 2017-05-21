package com.binarymonks.jj.core.specs.builders

import com.binarymonks.jj.core.specs.ThingSpec
import com.binarymonks.jj.core.specs.physics.PhysicsSpec


fun ThingSpec.physics(init: PhysicsSpec.() -> Unit): PhysicsSpec {
    this.physics.init()
    return this.physics
}

