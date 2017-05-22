package com.binarymonks.jj.core.pools

import com.badlogic.gdx.utils.Array

class Pool<POOLED>(internal var manager: PoolManager<POOLED>) {
    internal var pool = Array<POOLED>()

    val new: POOLED
        get() {
            if (pool.size == 0) {
                return manager.create_new()
            }
            return pool.pop()
        }

    fun add(pooled: POOLED) {
        manager.reset(pooled)
        pool.add(pooled)
    }

    fun clear() {
        for (pooled in pool) {
            manager.dispose(pooled)
        }
        pool.clear()
    }
}
