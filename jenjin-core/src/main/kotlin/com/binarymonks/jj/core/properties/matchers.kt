package com.binarymonks.jj.core.properties

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.Copyable
import com.binarymonks.jj.core.copy


interface Matcher : Copyable<Matcher> {
    fun matches(properties: ObjectMap<String, Any>): Boolean
}

class MatchAll : Matcher {
    override fun clone(): Matcher {
        return this
    }

    override fun matches(properties: ObjectMap<String, Any>): Boolean {
        return true
    }
}

class Key(var key: String? = null) : Matcher {
    override fun clone(): Matcher {
        return copy(this)
    }

    override fun matches(properties: ObjectMap<String, Any>): Boolean {
        return properties.containsKey(key)
    }
}

class KeyAndValue(var key: String? = null, var value: Any? = null) : Matcher {
    override fun clone(): Matcher {
        return copy(this)
    }

    override fun matches(properties: ObjectMap<String, Any>): Boolean {
        if (!properties.containsKey(key)) {
            return false
        }
        return properties[key] == value
    }
}

class AllOf() : Matcher {
    override fun clone(): Matcher {
        return copy(this)
    }

    var matchers = Array<Matcher>()

    constructor(build: AllOf.() -> Unit) : this() {
        this.build()
    }

    override fun matches(properties: ObjectMap<String, Any>): Boolean {
        matchers.forEach {
            if (!it.matches(properties)) {
                return false
            }
        }
        return true
    }

    fun key(key: String) {
        matchers.add(Key(key))
    }

    fun keyAndValue(key: String, value: Any?) {
        matchers.add(KeyAndValue(key, value))
    }

    fun <T : Matcher> not(matcher: T, build: (T.() -> Unit)? = null) {
        if (build != null) {
            matcher.build()
        }
        matchers.add(Not(matcher))
    }

    fun all(build: AllOf.() -> Unit) {
        val all = AllOf()
        all.build()
        matchers.add(all)
    }

    fun any(build: AnyOf.() -> Unit) {
        val all = AnyOf()
        all.build()
        matchers.add(all)
    }
}

class Not(var matcher: Matcher? = null) : Matcher {
    override fun clone(): Matcher {
        return copy(this)
    }
    override fun matches(properties: ObjectMap<String, Any>): Boolean {
        return !matcher!!.matches(properties)
    }
}

class AnyOf() : Matcher {
    var matchers = Array<Matcher>()

    override fun clone(): Matcher {
        return copy(this)
    }

    constructor(build: AnyOf.() -> Unit) : this() {
        this.build()
    }

    override fun matches(properties: ObjectMap<String, Any>): Boolean {
        matchers.forEach {
            if (it.matches(properties)) return true
        }
        return false
    }

    fun key(key: String) {
        matchers.add(Key(key))
    }

    fun keyAndValue(key: String, value: Any?) {
        matchers.add(KeyAndValue(key, value))
    }

    fun <T : Matcher> not(matcher: T, build: (T.() -> Unit)? = null) {
        if (build != null) {
            matcher.build()
        }
        matchers.add(Not(matcher))
    }

    fun all(build: AllOf.() -> Unit) {
        val all = AllOf()
        all.build()
        matchers.add(all)
    }

    fun any(build: AnyOf.() -> Unit) {
        val all = AnyOf()
        all.build()
        matchers.add(all)
    }
}