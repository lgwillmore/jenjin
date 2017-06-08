package com.binarymonks.jj.core.components

import com.binarymonks.jj.core.properties.PropOverride
import org.junit.Assert
import org.junit.Test


class ComponentTest {

    @Test
    fun clone() {

        val original = PrimitiveFields("name1", 2)
        original.copyable.set("blue")
        original.hiddenName = "altered"

        val copy: PrimitiveFields = original.clone() as PrimitiveFields

        Assert.assertNotSame(original, copy)
        Assert.assertEquals(original.name, copy.name)
        Assert.assertEquals(original.number, copy.number)
        Assert.assertNotEquals(original.hiddenName, copy.hiddenName)
        Assert.assertNotSame(original.copyable, copy.copyable)
        Assert.assertEquals(original.copyable, copy.copyable)
    }
}


class PrimitiveFields(
        var name: String? = null,
        var number: Int = 0,
        var copyable: PropOverride<String> = PropOverride("nothing")
) : Component() {
    internal var hiddenName: String = "hidden"
}

