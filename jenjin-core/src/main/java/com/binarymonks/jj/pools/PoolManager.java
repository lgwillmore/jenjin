package com.binarymonks.jj.pools;

/**
 * A PoolManager takes care of reseting and creating its THING_TO_POOL. This means we can pool anything.
 * @param <THING_TO_POOL>
 */
public interface PoolManager<THING_TO_POOL> {
    void reset(THING_TO_POOL thingToPool);
    THING_TO_POOL create_new();
}
