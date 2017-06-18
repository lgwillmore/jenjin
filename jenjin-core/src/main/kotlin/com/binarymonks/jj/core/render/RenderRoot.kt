package com.binarymonks.jj.core.render

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.render.nodes.RenderNode
import com.binarymonks.jj.core.specs.render.DefaultGraph
import com.binarymonks.jj.core.specs.render.LightGraph
import com.binarymonks.jj.core.scenes.Scene


class RenderRoot(var specID: Int) {
    internal var parent: Scene? = null
        set(value) {
            field = value
            setGraphParent(defaultRenderLayers, value)
            setGraphParent(lightRenderLayers, value)
        }
    internal var defaultRenderLayers: ObjectMap<Int, RenderLayer> = ObjectMap()
    internal var lightRenderLayers: ObjectMap<Int, RenderLayer> = ObjectMap()
    internal var namedNodes: ObjectMap<String, RenderNode> = ObjectMap()

    fun addNode(layer: Int, node: RenderNode) {
        if (layer < 0) {
            throw Exception("You cannot have a layer less than 0")
        }
        when (node.graphID) {
            is DefaultGraph -> addToGraph(defaultRenderLayers, layer, node)
            is LightGraph -> addToGraph(lightRenderLayers, layer, node)
            else -> throw Exception("unknown graph id")
        }
    }

    fun getNode(name: String): RenderNode? {
        return namedNodes.get(name)
    }

    private fun addToGraph(graphLayers: ObjectMap<Int, RenderLayer>, layer: Int, node: RenderNode) {
        if (!graphLayers.containsKey(layer)) {
            graphLayers.put(layer, RenderLayer())
        }
        graphLayers.get(layer).add(node)
        if (node.name != null) {
            namedNodes.put(node.name, node)
        }
        if (parent != null) {
            node.parent = parent
        }
    }

    private fun setGraphParent(graphLayers: ObjectMap<Int, RenderLayer>, parent: Scene?) {
        graphLayers.forEach {
            it.value.renderNodes.forEach {
                it.parent = parent
            }
        }
    }

    internal fun destroy(pooled: Boolean) {
        if (!pooled) {
            destroyGraph(defaultRenderLayers)
            destroyGraph(lightRenderLayers)
        }
    }

    private fun destroyGraph(graphLayers: ObjectMap<Int, RenderLayer>) {
        graphLayers.forEach {
            it.value.renderNodes.forEach {
                it.dispose()
            }
        }
    }

}