package com.binarymonks.jj.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ShortArray;
import com.binarymonks.jj.layers.LayerStack;
import com.binarymonks.jj.things.Thing;

public class RenderWorld {
    int renderIDCounter = 0;
    public SpriteBatch batch = new SpriteBatch();
    public ShapeRenderer shapeRenderer = new ShapeRenderer();
    public PolygonSpriteBatch polyBatch = new PolygonSpriteBatch();
    public RenderGraph defaultRenderGraph = new RenderGraph();
    public ObjectMap<Integer, PolygonSprite> polySpriteCache = new ObjectMap<>();
    EarClippingTriangulator triangulator = new EarClippingTriangulator();


    public void addThing(Thing thing) {
        //TODO:buildNew rendergraphs will be handled here
        defaultRenderGraph.add(thing.path, thing.id, thing.renderRoot.thingLayers);
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
}
