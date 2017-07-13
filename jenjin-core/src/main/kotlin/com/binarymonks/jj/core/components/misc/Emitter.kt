package com.binarymonks.jj.core.components.misc

import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.pools.recycle
import com.binarymonks.jj.core.specs.InstanceParams
import com.binarymonks.jj.core.specs.SceneSpecRef


class Emitter(
        var sceneSpecRef: SceneSpecRef? =null,
        var offsetX:Float = 0f,
        var offsetY:Float = 0f,
        var scaleX:Float = 1f,
        var scaleY:Float = 1f,
        var rotationD:Float = 0f,
        var delaySeconds:Float = 0f
) : Component() {

    private var scheduledID = -1

    override fun onAddToWorld() {
        scheduledID = JJ.clock.schedule(this::emit, delaySeconds, 0)
    }

    override fun onRemoveFromWorld() {
        JJ.clock.cancel(scheduledID)
    }

    fun emit() {
        val params = InstanceParams.new()
        val myPosition = me().physicsRoot.position()
        params.x = myPosition.x + offsetX
        params.y = myPosition.y + offsetY
        params.scaleX = scaleX
        params.scaleY = scaleY
        params.rotationD = rotationD

        JJ.scenes.instantiate(params, checkNotNull(sceneSpecRef).resolve())

        recycle(params)
    }

    override fun update() {

    }
}