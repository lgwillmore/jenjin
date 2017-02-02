package com.binarymonks.jj.pools.managers;

import com.badlogic.gdx.math.Matrix3;
import com.binarymonks.jj.pools.PoolManager;

public class Matrix3PoolManager implements PoolManager<Matrix3> {
    float[] clear = new float[]{1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};

    @Override
    public void reset(Matrix3 matrix3) {
        matrix3.set(clear);
    }

    @Override
    public Matrix3 create_new() {
        return new Matrix3();
    }

    @Override
    public void dispose(Matrix3 matrix3) {

    }
}
