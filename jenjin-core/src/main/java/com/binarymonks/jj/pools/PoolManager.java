package com.binarymonks.jj.pools;

/**
 * A PoolManager takes care of reseting and creating its POOLED_THING. This means we can pool anything.
 *
 * @param <POOLED_THING>
 */
public interface PoolManager<POOLED_THING> {
    /**
     * Called to reset each thing as it enters the pool
     *
     * @param pooledThing
     */
    void reset(POOLED_THING pooledThing);

    /**
     * Called when there are none in the pool
     *
     * @return
     */
    POOLED_THING create_new();

    /**
     * Called when the pool is being dumped. Clean up any assets or other entangling references.
     *
     * @param pooledThing
     */
    void dispose(POOLED_THING pooledThing);
}
