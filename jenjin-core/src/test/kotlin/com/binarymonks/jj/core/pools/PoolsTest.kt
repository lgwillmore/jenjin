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
        testObj.nuw(SceneToPool::class.java)
    }

    @Test
    fun getNew_Poolable() {
        val newScene = testObj.nuw(PoolableScene::class.java)
        Assert.assertNotNull(newScene)
    }

    @Test(expected = Pools.NoPoolManagerException::class)
    fun recycle_WhenNoPoolManager_NotPoolable() {
        testObj.recycle(SceneToPool())
    }

    @Test
    fun getNew_NotPoolable_WithPoolManagerRegistered() {
        val poolManager = ScenePoolManager(3, 5)
        testObj.registerManager(poolManager, SceneToPool::class.java)

        val actual = testObj.nuw(SceneToPool::class.java)

        Assert.assertEquals(actual.someValue.toLong(), poolManager.initialise_value.toLong())
    }

    @Test
    fun getNew_NotPoolable_AfterRecycling() {
        val poolManager = ScenePoolManager(3, 5)
        testObj.registerManager(poolManager, SceneToPool::class.java)

        var actual = testObj.nuw(SceneToPool::class.java)

        Assert.assertEquals(actual.someValue.toLong(), poolManager.initialise_value.toLong())

        testObj.recycle(actual)

        actual = testObj.nuw(SceneToPool::class.java)

        Assert.assertEquals(actual.someValue.toLong(), poolManager.reset_value.toLong())
    }

    @Test
    fun getNew_Poolable_AfterRecycling() {

        val actual = testObj.nuw(PoolableScene::class.java)


        testObj.recycle(actual)

        val actual_v2 = testObj.nuw(PoolableScene::class.java)

        Assert.assertSame(actual, actual_v2)
        Assert.assertTrue(actual.reset)

    }

    class ScenePoolManager(internal var initialise_value: Int, internal var reset_value: Int) : PoolManager<SceneToPool> {

        override fun reset(sceneToPool: SceneToPool) {
            sceneToPool.someValue = reset_value
        }

        override fun create_new(): SceneToPool {
            val newObj = SceneToPool()
            newObj.someValue = initialise_value
            return newObj
        }

        override fun dispose(sceneToPool: SceneToPool) {

        }
    }

    class SceneToPool {
        internal var someValue: Int = 0
    }

    class PoolableScene : Poolable {

        internal var reset = false

        override fun reset() {
            reset = true
        }

    }

}

