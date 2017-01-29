package com.binarymonks.jj.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.layers.LayerStack;

public class RenderWorld {
    public SpriteBatch batch = new SpriteBatch();
    public RenderGraph defaultRenderGraph = new RenderGraph();
}
