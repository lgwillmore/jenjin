package com.binarymonks.jj.core.components

import com.binarymonks.jj.core.properties.PropOverride
import com.binarymonks.jj.core.scenes.Scene
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito


class ComponentTest {

    @Test
    fun clone() {

        val original = PrimitiveFields("name1", 2)
        original.copyable.set("blue")
        original.hiddenName = "altered"
        original.scene = Mockito.mock(Scene::class.java)

        val copy: PrimitiveFields = original.clone() as PrimitiveFields

        Assert.assertNull(copy.scene)
        Assert.assertNotSame(original, copy)
        Assert.assertEquals(original.name, copy.name)
        Assert.assertEquals(original.number, copy.number)
        Assert.assertNotEquals(original.hiddenName, copy.hiddenName)
        Assert.assertNotSame(original.copyable, copy.copyable)
        Assert.assertEquals(original.copyable, copy.copyable)
        Assert.assertEquals(original.readableButNotCopyable, copy.readableButNotCopyable)
    }
}


class PrimitiveFields(
        var name: String? = null,
        var number: Int = 0,
        var copyable: PropOverride<String> = PropOverride("nothing")

) : Component() {
    var readableButNotCopyable: String = "Original"
        private set
    internal var hiddenName: String = "hidden"
    val fixed = "Cannot change"
}

