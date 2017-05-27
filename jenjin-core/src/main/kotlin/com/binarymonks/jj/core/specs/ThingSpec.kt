package com.binarymonks.jj.core.specs

import com.binarymonks.jj.core.audio.SoundParams
import com.binarymonks.jj.core.specs.physics.PhysicsSpec
import com.binarymonks.jj.core.specs.render.RenderSpec
import com.badlogic.gdx.utils.Array

internal var thingIDCounter = 0

class ThingSpec {
    val id = thingIDCounter++
    var physics: PhysicsSpec = PhysicsSpec()
    var render: RenderSpec = RenderSpec()
    var sounds : Array<SoundParams> = Array()
}