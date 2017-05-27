package com.binarymonks.jj.core.render

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.render.nodes.RenderNode
import com.binarymonks.jj.core.things.Thing


class RenderRoot(var specID: Int) {
    var parent: Thing? = null
        set(value) {
            defaultRenderLayers.forEach {
                it.value.renderNodes.forEach {
                    it.parent = value
                }
            }
        }
    var defaultRenderLayers: ObjectMap<Int, RenderLayer> = ObjectMap()

    fun addNode(layer: Int, node: RenderNode) {
        if (layer < 0) {
            throw Exception("You cannot have a layer less than 0")
        }
        if (!defaultRenderLayers.containsKey(layer)) {
            defaultRenderLayers.put(layer, RenderLayer())
        }
        defaultRenderLayers.get(layer).add(node)
        if (parent != null) {
            node.parent = parent
        }
    }

}