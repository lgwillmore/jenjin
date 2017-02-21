package com.binarymonks.jj.render;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ShortArray;
import com.binarymonks.jj.api.Lights;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.things.Thing;

public class RenderWorld implements Lights {
    public static final String DEFAULT_RENDER_GRAPH = "DEFAULT_RENDERGRAPH";
    public static final String LIGHTSOURCE_RENDER_GRAPH = "LIGHTSOURCE_RENDERGRAPH";
    int renderIDCounter = 0;
    public ShapeRenderer shapeRenderer = new ShapeRenderer();
    public PolygonSpriteBatch polyBatch = new PolygonSpriteBatch();
    public RayHandler rayHandler;
    public RenderGraph defaultRenderGraph = new RenderGraph();
    public RenderGraph lightSourceRenderGraph = new RenderGraph();
    public ObjectMap<Integer, PolygonSprite> polySpriteCache = new ObjectMap<>();
    EarClippingTriangulator triangulator = new EarClippingTriangulator();
    public float worldToScreenScale;
    private boolean currentShapeFill = false;

    public RenderWorld() {
        rayHandler = new RayHandler(Global.physics.world);
        rayHandler.setBlurNum(3);
        rayHandler.setAmbientLight(0.0f, 0.0f, 0.0f, 1f);
    }

    public void addThing(Thing thing) {
        defaultRenderGraph.add(thing.path, thing.id, thing.renderRoot.defaultThingLayers);
        lightSourceRenderGraph.add(thing.path, thing.id, thing.renderRoot.lightSourceThingLayers);
    }

    public int nextRenderID() {
        return renderIDCounter++;
    }

    public PolygonSprite polygonSprite(int renderSpecID, Array<Vector2> points) {
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(0xFFFFFFFF);
        pix.fill();
        Texture textureSolid = new Texture(pix);
        float[] vertices = new float[points.size * 2];
        ShortArray triangleIndices = triangulator.computeTriangles(vertices);
        for (int i = 0; i < points.size; i++) {
            Vector2 point = points.get(i);
            int offset = i * 2;
            vertices[offset] = point.x;
            vertices[offset + 1] = point.y;
        }
        PolygonRegion polyReg = new PolygonRegion(new TextureRegion(textureSolid),
                vertices, triangleIndices.toArray());
        PolygonSprite poly = new PolygonSprite(polyReg);
        polySpriteCache.put(renderSpecID, poly);
        return poly;
    }

    public void removeThing(Thing removal) {
        defaultRenderGraph.remove(removal.path, removal.id, removal.renderRoot.defaultThingLayers);
        lightSourceRenderGraph.remove(removal.path, removal.id, removal.renderRoot.lightSourceThingLayers);
    }

    @Override
    public void setAmbientLight(float r, float g, float b, float a) {
        rayHandler.setAmbientLight(r, g, b, a);
    }

    public void switchToShapes(boolean fill) {
        if (!shapeRenderer.isDrawing()) {
            polyBatch.end();
            shapeRenderer.begin(fill ? ShapeRenderer.ShapeType.Filled : ShapeRenderer.ShapeType.Line);
        } else if (fill != currentShapeFill) {
            currentShapeFill = fill;
            shapeRenderer.end();
            shapeRenderer.begin(fill ? ShapeRenderer.ShapeType.Filled : ShapeRenderer.ShapeType.Line);
        }
    }

    public void switchToBatch() {
        if (!polyBatch.isDrawing()) {
            shapeRenderer.end();
            polyBatch.begin();
        }
    }

    public void end() {
        if (polyBatch.isDrawing()) {
            polyBatch.end();
        } else {
            shapeRenderer.end();
        }
    }
}
