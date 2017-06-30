package com.binarymonks.jj.core.render

import com.badlogic.gdx.utils.ObjectMap


class RenderGraph {

    /**
     * render the layers in order
     */
    var graphLayers = ObjectMap<Int, GraphLayer>()


    /**
     * Add a [com.binarymonks.jj.core.scenes.Scene]s render nodes to the graph
     *
     * @param specID The [com.binarymonks.jj.core.render.RenderRoot.specID]
     * @param sceneID the [com.binarymonks.jj.core.scenes.Scene.id]
     */
    fun add(specID: Int, sceneID: Int, sceneLayers: ObjectMap<Int, RenderLayer>) {
        for (layer in sceneLayers) {
            if (!graphLayers.containsKey(layer.key)) {
                graphLayers.put(layer.key, GraphLayer())
            }
            graphLayers.get(layer.key).add(specID, sceneID, layer.value)
        }
    }

    fun remove(specID: Int, id: Int, sceneLayers: ObjectMap<Int, RenderLayer>) {
        for (layer in sceneLayers) {
            if (graphLayers.containsKey(layer.key)) {
                graphLayers.get(layer.key).remove(specID, id)
            }
        }
    }

    /**
     * We render the RenderLayer of each Scene in batches by their SceneSpec id.
     * This will maximise the advantages of SpriteBatch.
     */
    inner class GraphLayer {
        var sceneLayersByScenePathAndID = ObjectMap<Int, ObjectMap<Int, RenderLayer>>()

        fun add(specID: Int, sceneID: Int, sceneLayer: RenderLayer) {
            if (!sceneLayersByScenePathAndID.containsKey(specID)) {
                sceneLayersByScenePathAndID.put(specID, ObjectMap())
            }
            sceneLayersByScenePathAndID.get(specID).put(sceneID, sceneLayer)
        }

        fun remove(specID: Int, id: Int) {
            if (sceneLayersByScenePathAndID.containsKey(specID)) {
                sceneLayersByScenePathAndID.get(specID).remove(id)
            }
        }
    }
}
