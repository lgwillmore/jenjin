package com.binarymonks.jj.core.spine.components

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.spine.render.SpineRenderNode


val SPINE_RENDER_NAME = "SPINE_RENDER_NODE"

class SpineComponent : Component() {

    internal var spineBoneComponents: ObjectMap<String, SpineBoneComponent> = ObjectMap()
    var startingAnimation: String? = null
    internal var spineRenderNode: SpineRenderNode? = null
    internal var ragDoll = false

    fun addBone(name: String, spineBoneComponent: SpineBoneComponent) {
        spineBoneComponents.put(name, spineBoneComponent)
        spineBoneComponent.spineParent = this
    }


    override fun onAddToWorld() {
        spineRenderNode = thing().renderRoot.getNode(SPINE_RENDER_NAME) as SpineRenderNode?
        if (startingAnimation != null) {
            myRender().triggerAnimation(startingAnimation!!)
        }
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

    fun triggerRagDoll() {
        if (!ragDoll) {
            ragDoll = true
            for (partEntry in spineBoneComponents) {
                partEntry.value.triggerRagDoll()
            }
        }
    }
}