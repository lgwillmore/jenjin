package com.binarymonks.jj.core.pools

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PoolsTest {

    lateinit var testObj: Pools

    @Before
    fun setup() {
        testObj = Pools()
    }

    @Test(expected = Pools.NoPoolManagerException::class)
    fun getNew_WhenNoPoolManager_NotPoolable() {
        testObj.nuw(ThingToPool::class.java)
    }

    @Test
    fun getNew_Poolable() {
        val newThing = testObj.nuw(PoolableThing::class.java)
        Assert.assertNotNull(newThing)
    }

    @Test(expected = Pools.NoPoolManagerException::class)
    fun recycle_WhenNoPoolManager_NotPoolable() {
        testObj.recycle(ThingToPool())
    }

    @Test
    fun getNew_NotPoolable_WithPoolManagerRegistered() {
        val poolManager = ThingPoolManager(3, 5)
        testObj.registerManager(poolManager, ThingToPool::class.java)

        val actual = testObj.nuw(ThingToPool::class.java)

        Assert.assertEquals(actual.someValue.toLong(), poolManager.initialise_value.toLong())
    }

    @Test
    fun getNew_NotPoolable_AfterRecycling() {
        val poolManager = ThingPoolManager(3, 5)
        testObj.registerManager(poolManager, ThingToPool::class.java)

        var actual = testObj.nuw(ThingToPool::class.java)

        Assert.assertEquals(actual.someValue.toLong(), poolManager.initialise_value.toLong())

        testObj.recycle(actual)

        actual = testObj.nuw(ThingToPool::class.java)

        Assert.assertEquals(actual.someValue.toLong(), poolManager.reset_value.toLong())
    }

    @Test
    fun getNew_Poolable_AfterRecycling() {

        val actual = testObj.nuw(PoolableThing::class.java)


        testObj.recycle(actual)

        val actual_v2 = testObj.nuw(PoolableThing::class.java)

        Assert.assertSame(actual, actual_v2)
        Assert.assertTrue(actual.reset)

    }

    class ThingPoolManager(internal var initialise_value: Int, internal var reset_value: Int) : PoolManager<ThingToPool> {

        override fun reset(thingToPool: ThingToPool) {
            thingToPool.someValue = reset_value
        }

        override fun create_new(): ThingToPool {
            val newObj = ThingToPool()
            newObj.someValue = initialise_value
            return newObj
        }

        override fun dispose(thingToPool: ThingToPool) {

        }
    }

    class ThingToPool {
        internal var someValue: Int = 0
    }

    class PoolableThing : Poolable {

        internal var reset = false

        override fun reset() {
            reset = true
        }

    }

}

