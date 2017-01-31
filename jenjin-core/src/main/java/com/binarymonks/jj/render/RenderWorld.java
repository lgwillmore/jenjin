package com.binarymonks.jj.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.layers.LayerStack;
import com.binarymonks.jj.things.Thing;

public class RenderWorld {
    public SpriteBatch batch = new SpriteBatch();
    public ShapeRenderer shapeRenderer = new ShapeRenderer();
    public RenderGraph defaultRenderGraph = new RenderGraph();

    public void addThing(Thing thing) {
        //TODO:New rendergraphs will be handled here
        defaultRenderGraph.add(thing.path, thing.id,thing.renderRoot.thingLayers);
    }
}
