package com.binarymonks.jj.pools;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class PoolsTest {

    Pools test_obj;

    @Before
    public void setup() {
        test_obj = new Pools();
    }

    @Test(expected = Pools.NoPoolManagerException.class)
    public void getNew_WhenNoPoolManager() {
        this.test_obj.nuw(ThingToPool.class);
    }

    @Test(expected = Pools.NoPoolManagerException.class)
    public void recycle_WhenNoPoolManager() {
        this.test_obj.recycle(new ThingToPool());
    }

    @Test()
    public void getNew_WithPoolManagerRegistered() {
        ThingPoolManager poolManager = new ThingPoolManager(3, 5);
        this.test_obj.registerManager(poolManager, ThingToPool.class);

        ThingToPool actual = this.test_obj.nuw(ThingToPool.class);

        Assert.assertEquals(actual.someValue, poolManager.initialise_value);
    }

    @Test()
    public void getNew_AfterRecycling() {
        ThingPoolManager poolManager = new ThingPoolManager(3, 5);
        this.test_obj.registerManager(poolManager, ThingToPool.class);

        ThingToPool actual = this.test_obj.nuw(ThingToPool.class);

        Assert.assertEquals(actual.someValue, poolManager.initialise_value);

        this.test_obj.recycle(actual);

        actual = this.test_obj.nuw(ThingToPool.class);

        Assert.assertEquals(actual.someValue, poolManager.reset_value);
    }

    public static class ThingPoolManager implements PoolManager<ThingToPool> {
        int initialise_value;
        int reset_value;

        public ThingPoolManager(int initialize_value, int reset_value) {
            this.initialise_value = initialize_value;
            this.reset_value = reset_value;
        }

        @Override
        public void reset(ThingToPool thingToPool) {
            thingToPool.someValue = reset_value;
        }

        @Override
        public ThingToPool create_new() {
            ThingToPool newObj = new ThingToPool();
            newObj.someValue = initialise_value;
            return newObj;
        }
    }

    public static class ThingToPool {
        int someValue;
    }

}

