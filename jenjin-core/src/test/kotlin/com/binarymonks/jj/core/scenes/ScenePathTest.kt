package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.audio.SoundEffects
import com.binarymonks.jj.core.mockoutGDXinJJ
import com.binarymonks.jj.core.physics.PhysicsRoot
import com.binarymonks.jj.core.render.RenderRoot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class ScenePathTest {

    lateinit var grandParent: Scene
    lateinit var parent: Scene
    lateinit var uncle: Scene
    lateinit var me: Scene
    lateinit var cousin: Scene
    lateinit var child: Scene


    @Before
    fun setup() {
        mockoutGDXinJJ()
        grandParent = thing("grandparent")
        parent = thing("scene")
        uncle = thing("uncle")
        me = thing("me")
        cousin = thing("cousin")
        child = thing("child1")

        grandParent.addChild(uncle)
        grandParent.addChild(parent)
        parent.addChild(me)
        uncle.addChild(cousin)
        me.addChild(child)
    }

    @Test
    fun getGrandParent() {
        Assert.assertEquals(grandParent, ScenePath(UP, UP).from(me))
    }

    @Test
    fun getCousin(){
        Assert.assertEquals(cousin,ScenePath(UP,UP,"uncle","cousin").from(me))
    }

    @Test
    fun getChild(){
        Assert.assertEquals(child,ScenePath("child1").from(me))
    }

    @Test
    fun getMe(){
        Assert.assertEquals(me,ScenePath().from(me))
    }

    @Test(expected = Exception::class)
    fun getBadPath(){
        ScenePath("blah").from(me)
    }



    fun thing(name: String): Scene {
        return Scene(
                name,
                null,
                PhysicsRoot(Mockito.mock(Body::class.java)),
                RenderRoot(1),
                SoundEffects(),
                ObjectMap()
        )
    }
}