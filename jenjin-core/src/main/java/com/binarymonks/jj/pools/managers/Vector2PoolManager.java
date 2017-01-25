package com.binarymonks.jj.pools.managers;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.pools.PoolManager;

public class Vector2PoolManager implements PoolManager<Vector2> {
    @Override
    public void reset(Vector2 vector2) {
        vector2.set(0, 0);
    }

    @Override
    public Vector2 create_new() {
        return new Vector2();
    }
}
