package com.binarymonks.jj.pools;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.events.Event;
import com.binarymonks.jj.pools.managers.EventPoolManager;
import com.binarymonks.jj.pools.managers.Vector2PoolManager;

/**
 * Pools is a place to register new {@link PoolManager}s for things that you want to pool.
 * Then you can get and recycle the Pooled thing as you wish. There is one of these
 * ready to use and access from anywhere in {@link com.binarymonks.jj.JJ}
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

    public <T> T nuw(Class<T> pooled) {
        if (!pools.containsKey(pooled)) {
            throw new NoPoolManagerException(pooled);
        } else {
            return (T) pools.get(pooled).getNew();
        }
    }

    public void recycle(Object poolable) {
        if (!pools.containsKey(poolable.getClass())) {
            throw new NoPoolManagerException(poolable.getClass());
        } else {
            pools.get(poolable.getClass()).add(poolable);
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
