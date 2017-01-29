package com.binarymonks.jj.pools;

import com.badlogic.gdx.utils.Array;

public class Pool<POOLED> {

    PoolManager<POOLED> manager;
    Array<POOLED> pool = new Array<>();

    public Pool(PoolManager<POOLED> manager) {
        this.manager = manager;
    }

    public POOLED getNew() {
        if (pool.size == 0) {
            return manager.create_new();
        }
        return pool.pop();
    }

    public void add(POOLED pooled) {
        manager.reset(pooled);
        pool.add(pooled);
    }

    public void clear() {
        for (POOLED pooled : pool) {
            manager.dispose(pooled);
        }
        pool.clear();
    }
}
