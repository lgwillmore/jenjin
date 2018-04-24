package com.binarymonks.jj.core.components.misc

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.copy
import com.binarymonks.jj.core.pools.newObjectMap
import com.binarymonks.jj.core.pools.recycle
import com.binarymonks.jj.core.properties.PropOverride
import com.binarymonks.jj.core.specs.InstanceParams
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.core.specs.SceneSpecRefPath


class Emitter(
        val sceneSpecRef: PropOverride<SceneSpecRef?> = PropOverride(null),
        val offsetX: PropOverride<Float> = PropOverride(0f),
        val offsetY: PropOverride<Float> = PropOverride(0f),
        val scaleX: PropOverride<Float> = PropOverride(1f),
        val scaleY: PropOverride<Float> = PropOverride(1f),
        val rotationD: PropOverride<Float> = PropOverride(0f),
        val delayMinSeconds: PropOverride<Float> = PropOverride(1f),
        val delayMaxSeconds: PropOverride<Float> = PropOverride(1f),
        val repeat: PropOverride<Int> = PropOverride(0),
        val emitProps: PropOverride<ObjectMap<String, Any>> = PropOverride<ObjectMap<String, Any>>(newObjectMap())
) : Component() {

    fun setSpec(path: String) {
        sceneSpecRef.set(SceneSpecRefPath(path))
    }

    private var scheduledID = -1

    override fun onAddToWorld() {
        scheduledID = JJ.clock.schedule(this::emit, delayMinSeconds.get(), delayMaxSeconds.get(), repeat.get(), "Emitter")
    }

    override fun onRemoveFromWorld() {
        JJ.clock.cancel(scheduledID)
    }

    fun emit() {
        val params = InstanceParams.new()
        val myPosition = me().physicsRoot.position()
        val props = emitProps.get()
        params.x = myPosition.x + offsetX.get()
        params.y = myPosition.y + offsetY.get()
        params.scaleX = scaleX.get()
        params.scaleY = scaleY.get()
        params.rotationD = rotationD.get()
        params.properties = copy(props)

        JJ.scenes.instantiate(params, checkNotNull(sceneSpecRef.get()).resolve())

        recycle(params)
    }

    override fun update() {

    }
}