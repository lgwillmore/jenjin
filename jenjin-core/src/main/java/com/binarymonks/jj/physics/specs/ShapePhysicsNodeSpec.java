package com.binarymonks.jj.physics.specs;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.pools.N;


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
