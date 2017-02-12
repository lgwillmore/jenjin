package com.binarymonks.jj.pools.managers;

import com.badlogic.gdx.math.Vector3;
import com.binarymonks.jj.pools.PoolManager;

public class Vector3PoolManager implements PoolManager<Vector3> {
    @Override
    public void reset(Vector3 vector3) {
        vector3.set(0, 0, 0);
    }

    @Override
    public Vector3 create_new() {
        return new Vector3();
    }

    @Override
    public void dispose(Vector3 vector3) {

    }
}
