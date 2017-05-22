package com.binarymonks.jj.core.pools


import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.pools.managers.Matrix3PoolManager
import com.binarymonks.jj.core.pools.managers.Vector2PoolManager
import com.binarymonks.jj.core.pools.managers.Vector3PoolManager

fun <T> new(clazz: Class<T>): T {
    return JJ.pools.nuw(clazz)
}

fun recycle(vararg objects: Any) {
    for (o in objects) {
        JJ.pools.recycle(o)
    }
}

fun recycleItems(collectionOfObjects: Iterable<*>) {
    for (o in collectionOfObjects) {
        if (o != null) recycle(o)
    }
}

/**
 * If your thing is [Poolable] then you can just get new ones and recycle old ones here.
 * If not - A [PoolManager] must be registered.
 *
 * Pools is a place to register new [PoolManager]s for things that you want to pool.
 * Then you can get and recycle the Pooled thing as you wish with [new], [recycle] and [recycleItems]
 *
 */
class Pools {

    internal var pools = ObjectMap<Class<*>, Pool<*>>()
    internal var poolablePools = ObjectMap<Class<*>, Array<Poolable>>()

    init {
        registerManager(Vector2PoolManager(), Vector2::class.java)
        registerManager(Vector3PoolManager(), Vector3::class.java)
        registerManager(Matrix3PoolManager(), Matrix3::class.java)
    }

    /**
     * Get something from the pool or make a new one.
     *
     *
     * If your thing is [Poolable] then all is done for you.
     * If not - A [PoolManager] must be registered.
     *
     *
     * There is a nice little convenience function with much less to type [N.ew]
     * Be sure to [Re.cycle] it when you are done.

     * @param pooledClass the class of the object that is pooled
     * *
     * @param <T>
     * *
     * @return an instance of the pooled class
    </T> */
    fun <T> nuw(pooledClass: Class<T>): T {
        if (Poolable::class.java.isAssignableFrom(pooledClass)) {
            return nuwPoolable(pooledClass)
        }
        if (!pools.containsKey(pooledClass)) {
            throw NoPoolManagerException(pooledClass)
        } else {
            return pools.get(pooledClass).new as T
        }
    }

    private fun <T> nuwPoolable(pooledClass: Class<T>): T {
        if (poolablePools.containsKey(pooledClass) && poolablePools.get(pooledClass).size > 0) {
            return poolablePools.get(pooledClass).pop() as T
        } else {
            try {
                return pooledClass.newInstance()
            } catch (e: InstantiationException) {
                throw CouldNotCreateOneException(pooledClass)
            } catch (e: IllegalAccessException) {
                throw CouldNotCreateOneException(pooledClass)
            }

        }
    }

    /**
     * Recycle the used pooled object. A [PoolManager] must be registered.
     * There is a nice little convenience function with much less to type [Re.cycle]

     * @param pooled
     */
    fun recycle(pooled: Any) {
        if (pooled is Poolable) {
            recyclePoolable(pooled)
            return
        }
        if (!pools.containsKey(pooled.javaClass)) {
            throw NoPoolManagerException(pooled.javaClass)
        } else {
            pools.get(pooled.javaClass).add(pooled)
        }
    }

    private fun recyclePoolable(pooled: Poolable) {
        if (!poolablePools.containsKey(pooled.javaClass)) {
            poolablePools.put(pooled.javaClass, Array<Poolable>())
        }
        poolablePools.get(pooled.javaClass).add(pooled)
        pooled.reset()
    }

    /**
     * Use this if you want to dump the pooled objects.
     * They will be disposed by the PoolManager before being removed from the pool.

     * @param clazzToClear
     */
    fun clearPool(clazzToClear: Class<*>) {
        pools.get(clazzToClear).clear()
    }

    fun <P, T : PoolManager<P>> registerManager(poolManager: T, thingToPoolClass: Class<P>) {
        if (!pools.containsKey(thingToPoolClass)) {
            pools.put(thingToPoolClass, Pool(poolManager))
        }
    }

    class NoPoolManagerException(classWithMissingPool: Class<*>) : RuntimeException(String.format("No PoolManager for %s. Register a pool manager.", classWithMissingPool.canonicalName))

    class CouldNotCreateOneException(noDefault: Class<*>) : RuntimeException(String.format("Could not access a default constructor for %s. Check it exists, and check public static if nested.", noDefault.canonicalName))
}
