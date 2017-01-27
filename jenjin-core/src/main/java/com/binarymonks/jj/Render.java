package com.binarymonks.jj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.binarymonks.jj.layers.LayerStack;

public class Render {
    public Color clearColor = new Color(0, 0, 0, 1);
    public LayerStack layers = new LayerStack();

    Render() {
    }

    void update() {
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        layers.update();
    }

}
