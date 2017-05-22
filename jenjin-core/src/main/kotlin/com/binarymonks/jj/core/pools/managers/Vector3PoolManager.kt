package com.binarymonks.jj.core.pools.managers

import com.badlogic.gdx.math.Vector3
import com.binarymonks.jj.core.pools.PoolManager

class Vector3PoolManager : PoolManager<Vector3> {
    override fun reset(vector3: Vector3) {
        vector3.set(0f, 0f, 0f)
    }

    override fun create_new(): Vector3 {
        return Vector3()
    }

    override fun dispose(vector3: Vector3) {

    }
}
