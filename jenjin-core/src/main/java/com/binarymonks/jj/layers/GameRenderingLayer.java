package com.binarymonks.jj.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.render.RenderNode;
import com.binarymonks.jj.render.ThingLayer;
import com.binarymonks.jj.render.RenderGraph;

public class GameRenderingLayer implements Layer {
    public OrthographicCamera camera;
    Box2DDebugRenderer drenderer = new Box2DDebugRenderer();
    boolean b2dDebug = false;

    public GameRenderingLayer(float worldBoxWidth, float posX, float posY) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        camera = new OrthographicCamera(worldBoxWidth, worldBoxWidth * (h / w));
        camera.position.set(posX, posY, 0);
        camera.update();
    }


    @Override
    public void update() {
        camera.update();
        Global.renderWorld.batch.setProjectionMatrix(camera.combined);
        Global.renderWorld.polyBatch.setProjectionMatrix(camera.combined);
        Global.renderWorld.batch.begin();
        Global.renderWorld.polyBatch.begin();
        ObjectMap<Integer, RenderGraph.RenderLayer> layers = Global.renderWorld.defaultRenderGraph.renderLayers;
        int renderedCount = 0;
        int layerIndex=0;
        while (renderedCount < layers.size){
            if(layers.containsKey(layerIndex)){
                renderedCount++;
                updateLayer(layers.get(layerIndex));
            }
            layerIndex++;
        }
        Global.renderWorld.polyBatch.end();
        Global.renderWorld.batch.end();
        if (b2dDebug) {
            drenderer.render(Global.physics.world, Global.renderWorld.batch.getProjectionMatrix());
        }
    }

    public void setDebug(boolean debug){
        this.b2dDebug=true;
    }

    private void updateLayer(RenderGraph.RenderLayer layer) {
        for (ObjectMap.Entry<String, ObjectMap<Integer, ThingLayer>> componentsByThing : layer.thingLayersByThingPathAndID) {
            updateThingLayers(componentsByThing.value);
        }
    }

    private void updateThingLayers(ObjectMap<Integer, ThingLayer> thingLayers) {
        for (ObjectMap.Entry<Integer, ThingLayer> thingLayer : thingLayers) {
            updateThingNodes(thingLayer.value.renderNodes);
        }
    }

    private void updateThingNodes(Array<RenderNode> renderNodes) {
        for (RenderNode node : renderNodes) {
            node.render(camera);
        }
    }
}
