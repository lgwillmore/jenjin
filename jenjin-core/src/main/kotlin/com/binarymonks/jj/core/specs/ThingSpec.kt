package com.binarymonks.jj.core.specs

import com.binarymonks.jj.core.specs.physics.PhysicsSpec

internal var thingIDCounter=0

class ThingSpec {
    val id = thingIDCounter++
    var physics: PhysicsSpec = PhysicsSpec()
}