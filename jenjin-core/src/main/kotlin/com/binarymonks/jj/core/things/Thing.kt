package com.binarymonks.jj.core.things

import com.binarymonks.jj.core.physics.PhysicsRoot


class Thing(
        val uniqueName: String?,
        val physicsRoot: PhysicsRoot
) {
    init {
        physicsRoot.b2DBody.userData = this
    }


}