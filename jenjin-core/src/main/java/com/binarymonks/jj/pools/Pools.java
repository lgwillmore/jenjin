package com.binarymonks.jj.pools;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.events.Event;
import com.binarymonks.jj.pools.managers.EventPoolManager;
import com.binarymonks.jj.pools.managers.Vector2PoolManager;

/**
 * Pools is a place to register new {@link PoolManager}s for things that you want to pool.
 * Then you can get and recycle the Pooled thing as you wish. There is one of these
 * ready to use and access from anywhere in {@link com.binarymonks.jj.JJ}. Or even better at
 * {@link N#ew(Class)} and {@link Re#cycle(Object)}
 * <p>
 * Pools also comes with some default managers already registered for:
 * - {@link Event}
 * - {@link Vector2}
 */
public class Pools {

    ObjectMap<Class, Pool> pools = new ObjectMap<>();

    public Pools() {
        registerManager(new EventPoolManager(), Event.class);
        registerManager(new Vector2PoolManager(), Vector2.class);
    }

    /**
     * Get something from the pool or make a new one. A {@link PoolManager} must be registered.
     * There is a nice little convenience function with much less to type {@link N#ew(Class)}
     * Be sure to {@link Re#cycle(Object)} it when you are done.
     *
     * @param pooledClass the class of the object that is pooled
     * @param <T>
     * @return an instance of the pooled class
     */
    public <T> T nuw(Class<T> pooledClass) {
        if (!pools.containsKey(pooledClass)) {
            throw new NoPoolManagerException(pooledClass);
        } else {
            return (T) pools.get(pooledClass).getNew();
        }
    }

    /**
     * Recycle the used pooled object. A {@link PoolManager} must be registered.
     * There is a nice little convenience function with much less to type {@link Re#cycle(Object)}
     *
     * @param pooled
     */
    public void recycle(Object pooled) {
        if (!pools.containsKey(pooled.getClass())) {
            throw new NoPoolManagerException(pooled.getClass());
        } else {
            pools.get(pooled.getClass()).add(pooled);
        }
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
}
