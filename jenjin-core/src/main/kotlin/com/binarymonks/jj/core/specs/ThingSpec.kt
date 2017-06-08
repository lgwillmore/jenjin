package com.binarymonks.jj.core.specs

import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.audio.SoundParams
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.specs.physics.PhysicsSpec
import com.binarymonks.jj.core.specs.render.RenderSpec


class ThingSpec() {
    val id = JJ.B.nextID()
    var physics: PhysicsSpec = PhysicsSpec()
    var render: RenderSpec = RenderSpec()
    var sounds: Array<SoundParams> = Array()
    var components: Array<Component> = Array()

    constructor(build: ThingSpec.() -> Unit) : this() {
        this.build()
    }
}