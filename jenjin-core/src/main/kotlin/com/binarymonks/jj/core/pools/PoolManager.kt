package com.binarymonks.jj.core.pools

/**
 * A PoolManager takes care of resetting and creating its POOLED_THING. This means we can pool anything.

 * @param <POOLED_THING>
</POOLED_THING> */
interface PoolManager<POOLED_THING> {
    /**
     * Called to reset each thing as it enters the pool

     * @param pooledThing
     */
    fun reset(pooledThing: POOLED_THING)

    /**
     * Called when there are none in the pool

     * @return
     */
    fun create_new(): POOLED_THING

    /**
     * Called when the pool is being dumped. Clean up any assets or other entangling references.

     * @param pooledThing
     */
    fun dispose(pooledThing: POOLED_THING)
}
