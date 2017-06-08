package com.binarymonks.jj.core.render

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.render.nodes.RenderNode
import com.binarymonks.jj.core.specs.render.DefaultGraph
import com.binarymonks.jj.core.specs.render.LightGraph
import com.binarymonks.jj.core.things.Thing


class RenderRoot(var specID: Int) {
    var parent: Thing? = null
        set(value) {
            field = value
            setGraphParent(defaultRenderLayers, value)
            setGraphParent(lightRenderLayers, value)
        }
    var defaultRenderLayers: ObjectMap<Int, RenderLayer> = ObjectMap()
    var lightRenderLayers: ObjectMap<Int, RenderLayer> = ObjectMap()

    fun addNode(layer: Int, node: RenderNode) {
        if (layer < 0) {
            throw Exception("You cannot have a layer less than 0")
        }
        when (node.graphID) {
            is DefaultGraph -> addToGraph(defaultRenderLayers, layer, node)
            is LightGraph -> addToGraph(lightRenderLayers, layer, node)
        }


    }

    private fun addToGraph(graphLayers: ObjectMap<Int, RenderLayer>, layer: Int, node: RenderNode) {
        if (!graphLayers.containsKey(layer)) {
            graphLayers.put(layer, RenderLayer())
        }
        graphLayers.get(layer).add(node)
        if (parent != null) {
            node.parent = parent
        }
    }

    private fun setGraphParent(graphLayers: ObjectMap<Int, RenderLayer>, parent: Thing?) {
        graphLayers.forEach {
            it.value.renderNodes.forEach {
                it.parent = parent
            }
        }
    }

    internal fun destroy(pooled: Boolean) {
        destroyGraph(defaultRenderLayers)
        destroyGraph(lightRenderLayers)
    }

    private fun destroyGraph(graphLayers: ObjectMap<Int, RenderLayer>) {
        graphLayers.forEach {
            it.value.renderNodes.forEach {
                it.dispose()
            }
        }
    }

}