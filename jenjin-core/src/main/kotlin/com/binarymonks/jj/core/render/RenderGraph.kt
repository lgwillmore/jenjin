package com.binarymonks.jj.core.render


class RenderGraph {

//    /**
//     * render the layers in order
//     */
//    var renderLayers = ObjectMap<Int, RenderLayer>()
//
//
//    fun add(thingPath: String, thingID: Int, thingLayers: ObjectMap<Int, ThingLayer>) {
//        for (layer in thingLayers) {
//            if (!renderLayers.containsKey(layer.key)) {
//                renderLayers.put(layer.key, RenderLayer())
//            }
//            renderLayers.get(layer.key).add(thingPath, thingID, layer.value)
//        }
//    }
//
//    fun remove(path: String, id: Int, thingLayers: ObjectMap<Int, ThingLayer>) {
//        for (layer in thingLayers) {
//            if (renderLayers.containsKey(layer.key)) {
//                renderLayers.get(layer.key).remove(path, id)
//            }
//        }
//    }
//
//    /**
//     * We render the ThingLayer of each Thing in batches by their ThingSpec path.
//     * This will maximise the advantages of SpriteBatch.
//     */
//    inner class RenderLayer {
//        var thingLayersByThingPathAndID = ObjectMap<String, ObjectMap<Int, ThingLayer>>()
//
//        fun add(thingPath: String, thingID: Int, thingLayer: ThingLayer) {
//            if (!thingLayersByThingPathAndID.containsKey(thingPath)) {
//                thingLayersByThingPathAndID.put(thingPath, ObjectMap())
//            }
//            thingLayersByThingPathAndID.get(thingPath).put(thingID, thingLayer)
//        }
//
//        fun remove(path: String, id: Int) {
//            if (thingLayersByThingPathAndID.containsKey(path)) {
//                thingLayersByThingPathAndID.get(path).remove(id)
//            }
//        }
//    }
}
