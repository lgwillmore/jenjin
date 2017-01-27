package com.binarymonks.jj.layers;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DefaultLayer implements Layer {

    ShapeRenderer shapeRenderer;

    public DefaultLayer() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(0, 0, 50, 50);
        shapeRenderer.end();
    }
}
