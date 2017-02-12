package com.binarymonks.jj.pools;


import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.async.FunctionLink;
import com.binarymonks.jj.physics.CollisionGroups;
import com.binarymonks.jj.pools.managers.Matrix3PoolManager;
import com.binarymonks.jj.pools.managers.Vector2PoolManager;
import com.binarymonks.jj.pools.managers.Vector3PoolManager;
import com.binarymonks.jj.things.InstanceParams;

/**
 * If your thing is {@link Poolable} thenDo you can just checkPools new ones and recycle old ones here.
 * If not - A {@link PoolManager} must be registered.
 * <p>
 * Pools is a place to register new {@link PoolManager}s for things that you want to pool.
 * Then you can checkPools and recycle the Pooled thing as you wish. There is one of these
 * ready to use and access from anywhere in {@link com.binarymonks.jj.JJ}. Or even better at
 * {@link N#ew(Class)} and {@link Re#cycle(Object)}
 * <p>
 * Pools also comes with some default managers already registered for:
 * - {@link Vector2}
 * - {@link Matrix3}
 */
public class Pools {

    ObjectMap<Class, Pool> pools = new ObjectMap<>();
    ObjectMap<Class, Array<Poolable>> poolablePools = new ObjectMap<>();

    public Pools() {
        registerManager(new Vector2PoolManager(), Vector2.class);
        registerManager(new Vector3PoolManager(), Vector3.class);
        registerManager(new InstanceParams.PM(), InstanceParams.class);
        registerManager(new Matrix3PoolManager(), Matrix3.class);
        registerManager(new FunctionLink.FunctionLinkPoolManager(), FunctionLink.class);
        registerManager(new CollisionGroups.CollisionDataPoolManager(), CollisionGroups.CollisionData.class);
    }

    /**
     * Get something from the pool or make a new one.
     * <p>
     * If your thing is {@link Poolable} thenDo all is done for you.
     * If not - A {@link PoolManager} must be registered.
     * <p>
     * There is a nice little convenience function with much less to type {@link N#ew(Class)}
     * Be sure to {@link Re#cycle(Object)} it when you are done.
     *
     * @param pooledClass the class of the object that is pooled
     * @param <T>
     * @return an instance of the pooled class
     */
    public <T> T nuw(Class<T> pooledClass) {
        if (Poolable.class.isAssignableFrom(pooledClass)) {
            return nuwPoolable(pooledClass);
        }
        if (!pools.containsKey(pooledClass)) {
            throw new NoPoolManagerException(pooledClass);
        } else {
            return (T) pools.get(pooledClass).getNew();
        }
    }

    private <T> T nuwPoolable(Class<T> pooledClass) {
        if (poolablePools.containsKey(pooledClass) && poolablePools.get(pooledClass).size > 0) {
            return (T) poolablePools.get(pooledClass).pop();
        } else {
            try {
                return pooledClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new NoDefaultConstructorException(pooledClass);
            }
        }
    }

    /**
     * Recycle the used pooled object. A {@link PoolManager} must be registered.
     * There is a nice little convenience function with much less to type {@link Re#cycle(Object)}
     *
     * @param pooled
     */
    public void recycle(Object pooled) {
        if (pooled instanceof Poolable) {
            recyclePoolable((Poolable) pooled);
            return;
        }
        if (!pools.containsKey(pooled.getClass())) {
            throw new NoPoolManagerException(pooled.getClass());
        } else {
            pools.get(pooled.getClass()).add(pooled);
        }
    }

    private void recyclePoolable(Poolable pooled) {
        if (!poolablePools.containsKey(pooled.getClass())) {
            poolablePools.put(pooled.getClass(), new Array<>());
        }
        poolablePools.get(pooled.getClass()).add(pooled);
        pooled.reset();
    }

    /**
     * Use this if you want to dump the pooled objects.
     * They will be disposed by the PoolManager before being removed from the pool.
     *
     * @param clazzToClear
     */
    public void clearPool(Class clazzToClear) {
        pools.get(clazzToClear).clear();
    }

    public <P, T extends PoolManager<P>> void registerManager(T poolManager, Class<P> thingToPoolClass) {
        if (!pools.containsKey(thingToPoolClass)) {
            pools.put(thingToPoolClass, new Pool<>(poolManager));
        }
    }

    public static class NoPoolManagerException extends RuntimeException {
        public NoPoolManagerException(Class<?> classWithMissingPool) {
            super(String.format("No PoolManager for %s. Register a pool manager.", classWithMissingPool.getCanonicalName()));
        }
    }

    public static class NoDefaultConstructorException extends RuntimeException {
        public NoDefaultConstructorException(Class<?> noDefault) {
            super(String.format("No Default constructor for %s. Make one to pool it.", noDefault.getCanonicalName()));
        }
    }
}
