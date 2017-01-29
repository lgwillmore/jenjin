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
    boolean b2dDebug = true;

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
        Global.renderWorld.batch.begin();
        for (RenderGraph.RenderLayer layer : Global.renderWorld.defaultRenderGraph.renderLayers) {
            updateLayer(layer);
        }
        Global.renderWorld.batch.end();
        if (b2dDebug) {
            drenderer.render(Global.physics.world, Global.renderWorld.batch.getProjectionMatrix());
        }
    }

    private void updateLayer(RenderGraph.RenderLayer layer) {
        for (ObjectMap.Entry<String, ObjectMap<Integer, ThingLayer>> componentsByThing : layer.thingLayersByThingPath) {
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
            node.render();
        }
    }
}
