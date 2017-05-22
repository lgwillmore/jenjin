package com.binarymonks.jj.core.pools.managers

import com.badlogic.gdx.math.Matrix3
import com.binarymonks.jj.core.pools.PoolManager

class Matrix3PoolManager : PoolManager<Matrix3> {
    internal var clear = floatArrayOf(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f)

    override fun reset(matrix3: Matrix3) {
        matrix3.set(clear)
    }

    override fun create_new(): Matrix3 {
        return Matrix3()
    }

    override fun dispose(matrix3: Matrix3) {

    }
}
