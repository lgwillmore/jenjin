package com.binarymonks.jj.core.components.spine

import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.render.nodes.SpineRenderNode


val SPINE_RENDER_NAME = "SPINE_RENDER_NODE"

class SpineComponent : Component() {

    var startingAnimation: String? = null
    internal var spineRenderNode: SpineRenderNode? = null


    override fun onAddToWorld() {
        spineRenderNode = myThing().renderRoot.getNode(SPINE_RENDER_NAME) as SpineRenderNode?
        if (startingAnimation != null) {
            myRender().triggerAnimation(startingAnimation!!)
        }
    }

    fun myRender(): SpineRenderNode {
        return checkNotNull(spineRenderNode)
    }
}