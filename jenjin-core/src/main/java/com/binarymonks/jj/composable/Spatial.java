package com.binarymonks.jj.composable;

import com.binarymonks.jj.interfaces.Cloneable;

public class Spatial implements Cloneable<Spatial> {

    public float x;
    public float y;
    public float rotationD;

    public Spatial setX(float x) {
        this.x = x;
        return this;
    }

    public Spatial setY(float y) {
        this.y = y;
        return this;
    }

    public Spatial setRotationD(float rotationD) {
        this.rotationD = rotationD;
        return this;
    }

    @Override
    public Spatial clone() {
        return new Spatial().setX(x).setY(y).setRotationD(rotationD);
    }
}
