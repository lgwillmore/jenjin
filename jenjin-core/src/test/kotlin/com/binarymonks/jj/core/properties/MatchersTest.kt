package com.binarymonks.jj.core.properties

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.extensions.build
import org.junit.Assert
import org.junit.Test

class MatchersTest {

    val key1 = "Key1"
    val key2 = "Key2"
    val value1 = "Value1"
    val value2 = "Value2"

    val props = ObjectMap<String, Any>().build {
        put(key1, value1)
        put(key2, value2)
    }

    @Test
    fun key_writeKey() {
        val matcher = Key(key1)

        Assert.assertTrue(matcher.matches(props))
    }

    @Test
    fun key_wrongKey() {
        val matcher = Key("other")

        Assert.assertFalse(matcher.matches(props))
    }

    @Test
    fun keyAndValue_wrongKey_rightValue() {
        val matcher = KeyAndValue("other", value1)
        Assert.assertFalse(matcher.matches(props))
    }

    @Test
    fun keyAndValue_rightKey_wrongValue() {
        val matcher = KeyAndValue(key1, "Other")
        Assert.assertFalse(matcher.matches(props))
    }

    @Test
    fun keyAndValue_rightKey_rightValue() {
        val matcher = KeyAndValue(key1, value1)
        Assert.assertTrue(matcher.matches(props))
    }

    @Test
    fun not_innerMatches() {
        val matcher = Not(StubMatch(true))
        Assert.assertFalse(matcher.matches(props))
    }

    @Test
    fun not_innerDoesNotMatch() {
        val matcher = Not(StubMatch(false))
        Assert.assertTrue(matcher.matches(props))
    }

    @Test
    fun any_someMatch() {
        val matcher = AnyOf {
            matchers.add(StubMatch(false))
            matchers.add(StubMatch(true))
        }
        Assert.assertTrue(matcher.matches(props))
    }

    @Test
    fun any_noneMatch() {
        val matcher = AnyOf {
            matchers.add(StubMatch(false))
            matchers.add(StubMatch(false))
        }
        Assert.assertFalse(matcher.matches(props))
    }

    @Test
    fun all_allMatch() {
        val matcher = AllOf {
            matchers.add(StubMatch(true))
            matchers.add(StubMatch(true))
        }
        Assert.assertTrue(matcher.matches(props))
    }

    @Test
    fun all_someMatch() {
        val matcher = AllOf {
            matchers.add(StubMatch(true))
            matchers.add(StubMatch(false))
        }
        Assert.assertFalse(matcher.matches(props))
    }

}

class StubMatch(val result: Boolean) : Matcher {
    override fun clone(): Matcher {
        return this
    }

    override fun matches(properties: ObjectMap<String, Any>): Boolean {
        return result
    }
}