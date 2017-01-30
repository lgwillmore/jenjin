package com.binarymonks.jj.render;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;


public class RenderGraph {

    /**
     * RenderWorld the layers in layer
     */
    public Array<RenderLayer> renderLayers = new Array<>();

    /**
     * We renderWorld the ThingLayer of each Thing in batches by their ThingSpec path.
     * This will maximise the advantages of SpriteBatch.
     */
    public class RenderLayer {
        public ObjectMap<String, ObjectMap<Integer, ThingLayer>> thingLayersByThingPath = new ObjectMap<>();
    }
}
