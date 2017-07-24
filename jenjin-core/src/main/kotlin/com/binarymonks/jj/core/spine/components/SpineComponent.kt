package com.binarymonks.jj.core.spine.components

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.scenes.ScenePath
import com.binarymonks.jj.core.spine.render.SpineRenderNode


val SPINE_RENDER_NAME = "SPINE_RENDER_NODE"

class SpineComponent : Component() {

    internal var spineBoneComponents: ObjectMap<String, SpineBoneComponent> = ObjectMap()
    var startingAnimation: String? = null
    internal var spineRenderNode: SpineRenderNode? = null
    internal var ragDoll = false
    var bonePaths: ObjectMap<String, ScenePath> = ObjectMap()

    override fun onAddToWorld() {
        spineRenderNode = me().renderRoot.getNode(SPINE_RENDER_NAME) as SpineRenderNode?
        bonePaths.forEach { it.value.from(me()).getComponent(SpineBoneComponent::class)!!.setSpineComponent(me()) }
        if (startingAnimation != null) {
            myRender().triggerAnimation(startingAnimation!!)
        }
    }

    fun addBone(name: String, spineBoneComponent: SpineBoneComponent) {
        spineBoneComponents.put(name, spineBoneComponent)
        spineBoneComponent.spineParent = this
    }

    fun getBone(name: String): SpineBoneComponent {
        return spineBoneComponents.get(name)
    }


    override fun onRemoveFromWorld() {
        spineBoneComponents.clear()
    }

    fun myRender(): SpineRenderNode {
        return checkNotNull(spineRenderNode)
    }

    fun reverseRagDoll() {
        if (ragDoll) {
            ragDoll = false
            for (partEntry in spineBoneComponents) {
                partEntry.value.reverseRagDoll()
            }
        }
    }

    fun triggerRagDoll(gravity: Float = 1f) {
        if (!ragDoll) {
            ragDoll = true
            for (partEntry in spineBoneComponents) {
                partEntry.value.triggerRagDoll(gravity)
            }
        }
    }
}