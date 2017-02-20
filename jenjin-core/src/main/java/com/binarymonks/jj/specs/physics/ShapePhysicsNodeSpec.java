package com.binarymonks.jj.specs.physics;


public class ShapePhysicsNodeSpec implements PhysicsNodeSpec {

    float offsetX;
    float offsetY;
    float rotationD;

    @Override
    public float getOffsetX() {
        return offsetX;
    }

    @Override
    public float getOffsetY() {
        return offsetY;
    }

    @Override
    public float getRotationD() {
        return rotationD;
    }
}
