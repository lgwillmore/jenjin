package com.binarymonks.jj.composable;

import com.binarymonks.jj.interfaces.Cloneable;

/**
 * Created by lwillmore on 30/01/17.
 */
public class Dimension implements Cloneable<Dimension> {

    public float width;
    public float height;

    public Dimension setWidth(float width) {
        this.width = width;
        return this;
    }

    public Dimension setHeight(float height) {
        this.height = height;
        return this;
    }

    public Dimension clone() {
        return new Dimension().setHeight(height).setWidth(width);
    }
}
