package com.binarymonks.jj.core.render

import com.badlogic.gdx.utils.ObjectMap


class RenderGraph {

    /**
     * render the layers in order
     */
    var graphLayers = ObjectMap<Int, GraphLayer>()


    /**
     * Add a [com.binarymonks.jj.core.things.Thing]s render nodes to the graph
     *
     * @param specID The [com.binarymonks.jj.core.render.RenderRoot.specID]
     * @param thingID the [com.binarymonks.jj.core.things.Thing.id]
     */
    fun add(specID: Int, thingID: Int, thingLayers: ObjectMap<Int, RenderLayer>) {
        for (layer in thingLayers) {
            if (!graphLayers.containsKey(layer.key)) {
                graphLayers.put(layer.key, GraphLayer())
            }
            graphLayers.get(layer.key).add(specID, thingID, layer.value)
        }
    }

    fun remove(specID: Int, id: Int, thingLayers: ObjectMap<Int, RenderLayer>) {
        for (layer in thingLayers) {
            if (graphLayers.containsKey(layer.key)) {
                graphLayers.get(layer.key).remove(specID, id)
            }
        }
    }

    /**
     * We render the RenderLayer of each Scene in batches by their ThingSpec id.
     * This will maximise the advantages of SpriteBatch.
     */
    inner class GraphLayer {
        var thingLayersByThingPathAndID = ObjectMap<Int, ObjectMap<Int, RenderLayer>>()

        fun add(specID: Int, thingID: Int, thingLayer: RenderLayer) {
            if (!thingLayersByThingPathAndID.containsKey(specID)) {
                thingLayersByThingPathAndID.put(specID, ObjectMap())
            }
            thingLayersByThingPathAndID.get(specID).put(thingID, thingLayer)
        }

        fun remove(specID: Int, id: Int) {
            if (thingLayersByThingPathAndID.containsKey(specID)) {
                thingLayersByThingPathAndID.get(specID).remove(id)
            }
        }
    }
}
