package com.binarymonks.jj.pools;

/**
 * A PoolManager takes care of reseting and creating its POOLED_THING. This means we can pool anything.
 * @param <POOLED_THING>
 */
public interface PoolManager<POOLED_THING> {
    void reset(POOLED_THING pooled_thing);
    POOLED_THING create_new();
}
