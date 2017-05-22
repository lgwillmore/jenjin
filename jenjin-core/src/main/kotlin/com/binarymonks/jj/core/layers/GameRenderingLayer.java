package com.binarymonks.jj.core.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ObjectMap;


public class GameRenderingLayer extends Layer {
    public OrthographicCamera camera;

    Box2DDebugRenderer drenderer = new Box2DDebugRenderer();

    public GameRenderingLayer(float worldBoxWidth, float posX, float posY) {
        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        camera = new OrthographicCamera();
        setView(worldBoxWidth, posX, posY);
    }


    @Override
    public void update() {
        camera.update();
        updateScreenToWorldScale();
//        renderGraph(JJ.B.renderWorld.defaultRenderGraph);
        renderLights();
//        renderGraph(Global.renderWorld.lightSourceRenderGraph);
//        if (Global.config.b2dDebug) {
//            drenderer.render(Global.physics.world, Global.renderWorld.polyBatch.getProjectionMatrix());
//        }
    }

    private void updateScreenToWorldScale() {
        float worldDistance = 1000;
        Vector3 worldLeft = N.ew(Vector3.class).set(0, 0, 0);
        Vector3 worldRight = N.ew(Vector3.class).set(worldDistance, 0, 0);
        Vector3 screenLeft = camera.project(worldLeft);
        Vector3 screenRight = camera.project(worldRight);
        Global.renderWorld.worldToScreenScale = (screenRight.x - screenLeft.x) / worldDistance;
    }

    private void renderGraph(RenderGraph renderGraph) {
        ObjectMap<Integer, RenderGraph.RenderLayer> layers = renderGraph.renderLayers;
        Global.renderWorld.polyBatch.enableBlending();
        Global.renderWorld.polyBatch.setProjectionMatrix(camera.combined);
        Global.renderWorld.polyBatch.begin();
        int renderedCount = 0;
        int layerIndex = 0;
        while (renderedCount < layers.size) {
            if (layers.containsKey(layerIndex)) {
                renderedCount++;
                updateLayer(layers.get(layerIndex));
            }
            layerIndex++;
        }
        Global.renderWorld.end();
    }

    private void renderLights() {
        Global.renderWorld.rayHandler.setCombinedMatrix(camera.combined, camera.position.x, camera.position.y, camera.viewportWidth, camera.viewportHeight);
        Global.renderWorld.rayHandler.updateAndRender();
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

    public void setView(float worldWidth, float cameraX, float cameraY) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera.viewportWidth = worldWidth;
        camera.viewportHeight = worldWidth * (h / w);
        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }
}
