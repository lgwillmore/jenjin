package com.binarymonks.jj.core.specs.builders

import com.binarymonks.jj.core.specs.ThingSpec
import com.binarymonks.jj.core.specs.physics.PhysicsSpec
import com.binarymonks.jj.core.specs.render.RenderSpec


fun ThingSpec.physics(init: PhysicsSpec.() -> Unit): PhysicsSpec {
    this.physics.init()
    return this.physics
}

fun ThingSpec.render(init: RenderSpec.() -> Unit) {
    this.render.init()
}

