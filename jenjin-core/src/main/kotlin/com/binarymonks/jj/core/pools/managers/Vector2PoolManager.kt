package com.binarymonks.jj.core.pools.managers

import com.badlogic.gdx.math.Vector2
import com.binarymonks.jj.core.pools.PoolManager

class Vector2PoolManager : PoolManager<Vector2> {
    override fun reset(vector2: Vector2) {
        vector2.set(0f, 0f)
    }

    override fun create_new(): Vector2 {
        return Vector2()
    }

    override fun dispose(vector2: Vector2) {

    }
}
