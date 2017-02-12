package com.binarymonks.jj.render;

import com.badlogic.gdx.utils.ObjectMap;


public class RenderGraph {

    /**
     * render the layers in order
     */
    public ObjectMap<Integer, RenderLayer> renderLayers = new ObjectMap<>();


    public void add(String thingPath, int thingID, ObjectMap<Integer, ThingLayer> thingLayers) {
        for (ObjectMap.Entry<Integer, ThingLayer> layer : thingLayers) {
                if (!renderLayers.containsKey(layer.key)) {
                    renderLayers.put(layer.key, new RenderLayer());
                }
                renderLayers.get(layer.key).add(thingPath, thingID, layer.value);
        }
    }

    public void remove(String path, int id, ObjectMap<Integer, ThingLayer> thingLayers) {
        for (ObjectMap.Entry<Integer, ThingLayer> layer : thingLayers) {
            if (renderLayers.containsKey(layer.key)) {
                renderLayers.get(layer.key).remove(path, id);
            }
        }
    }

    /**
     * We render the ThingLayer of each Thing in batches by their ThingSpec path.
     * This will maximise the advantages of SpriteBatch.
     */
    public class RenderLayer {
        public ObjectMap<String, ObjectMap<Integer, ThingLayer>> thingLayersByThingPathAndID = new ObjectMap<>();

        public void add(String thingPath, int thingID, ThingLayer thingLayer) {
            if (!thingLayersByThingPathAndID.containsKey(thingPath)) {
                thingLayersByThingPathAndID.put(thingPath, new ObjectMap<>());
            }
            thingLayersByThingPathAndID.get(thingPath).put(thingID, thingLayer);
        }

        public void remove(String path, int id) {
            if (thingLayersByThingPathAndID.containsKey(path)) {
                thingLayersByThingPathAndID.get(path).remove(id);
            }
        }
    }
}
