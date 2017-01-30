package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by lwillmore on 30/01/17.
 */
public class DrawSpec<T extends RenderSpec.Shape> {

    public Color color = Color.YELLOW;
    public boolean fill = true;

    T parent;

    public DrawSpec(T parent) {
        this.parent = parent;
    }

    public T setColor(Color color) {
        this.color = color;
        return parent;
    }

    public T setFill(boolean fill) {
        this.fill = fill;
        return parent;
    }
}
