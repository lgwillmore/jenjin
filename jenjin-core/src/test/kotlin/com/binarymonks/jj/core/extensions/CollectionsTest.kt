package com.binarymonks.jj.core.extensions

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectSet
import com.binarymonks.jj.core.mockoutGDXinJJ
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class CollectionsTest {

    @Before
    fun setUp(){
        mockoutGDXinJJ()
    }

    @Test
    fun array_random() {
        val array: Array<Int> = Array()
        array.addVar(1, 2)

        var oneFound = false
        var twoFound = false
        for (i in 0..20) {
            when (array.random()) {
                1 -> oneFound = true
                2 -> twoFound = true
                else -> Unit
            }
        }
        Assert.assertTrue(oneFound)
        Assert.assertTrue(twoFound)
    }

    @Test
    fun objectSet_RandomMembers() {
        val sourceSet = ObjectSet<Int>()
        val targetSet = ObjectSet<Int>()
        sourceSet.add(1)
        sourceSet.add(2)
        sourceSet.add(3)

        targetSet.add(4)

        sourceSet.randomMembers(2, targetSet)

        Assert.assertTrue(targetSet.size == 3)
        Assert.assertTrue(targetSet.contains(4))
        targetSet.forEach {
            Assert.assertTrue(it in 1..4)
        }
    }

    @Test(expected = Exception::class)
    fun objectSet_RandomMembers_selectTooMany(){
        val sourceSet = ObjectSet<Int>()
        val targetSet = ObjectSet<Int>()
        sourceSet.add(1)
        sourceSet.add(2)
        sourceSet.add(3)

        sourceSet.randomMembers(4,targetSet)

    }

    @Test(expected = Exception::class)
    fun objectSet_RandomMembers_selectNegative(){
        val sourceSet = ObjectSet<Int>()
        val targetSet = ObjectSet<Int>()
        sourceSet.add(1)
        sourceSet.add(2)
        sourceSet.add(3)

        sourceSet.randomMembers(-1,targetSet)

    }

    @Test
    fun arrayt_RandomMembers() {
        val sourceSet = Array<Int>()
        val targetSet = Array<Int>()
        sourceSet.add(1)
        sourceSet.add(2)
        sourceSet.add(3)

        targetSet.add(4)

        sourceSet.randomMembers(2, targetSet)

        Assert.assertTrue(targetSet.size == 3)
        Assert.assertTrue(targetSet.contains(4))
        targetSet.forEach {
            Assert.assertTrue(it in 1..4)
        }
    }

    @Test(expected = Exception::class)
    fun array_RandomMembers_selectTooMany(){
        val sourceSet = Array<Int>()
        val targetSet = Array<Int>()
        sourceSet.add(1)
        sourceSet.add(2)
        sourceSet.add(3)

        sourceSet.randomMembers(4,targetSet)

    }

    @Test(expected = Exception::class)
    fun array_RandomMembers_selectNegative(){
        val sourceSet = Array<Int>()
        val targetSet = Array<Int>()
        sourceSet.add(1)
        sourceSet.add(2)
        sourceSet.add(3)

        sourceSet.randomMembers(-1,targetSet)

    }


}