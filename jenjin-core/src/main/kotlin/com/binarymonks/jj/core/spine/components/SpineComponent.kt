package com.binarymonks.jj.core.spine.components

import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.spine.render.SpineRenderNode


val SPINE_RENDER_NAME = "SPINE_RENDER_NODE"

class SpineComponent : Component() {

    var startingAnimation: String? = null
    internal var spineRenderNode: SpineRenderNode? = null


    override fun onAddToWorld() {
        spineRenderNode = thing().renderRoot.getNode(SPINE_RENDER_NAME) as SpineRenderNode?
        if (startingAnimation != null) {
            myRender().triggerAnimation(startingAnimation!!)
        }
    }

    fun myRender(): SpineRenderNode {
        return checkNotNull(spineRenderNode)
    }
}