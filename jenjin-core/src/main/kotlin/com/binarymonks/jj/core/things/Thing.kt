package com.binarymonks.jj.core.things

import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.physics.PhysicsRoot
import com.binarymonks.jj.core.render.RenderRoot


class Thing(
        val uniqueName: String?,
        val physicsRoot: PhysicsRoot,
        val renderRoot: RenderRoot
) {
    var id = JJ.B.nextID()

    init {
        physicsRoot.parent = this
        renderRoot.parent = this
    }


}