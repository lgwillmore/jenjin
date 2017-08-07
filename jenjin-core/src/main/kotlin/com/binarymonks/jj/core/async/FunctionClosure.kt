package com.binarymonks.jj.core.async

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.pools.Poolable
import com.binarymonks.jj.core.pools.recycle
import kotlin.reflect.KFunction


class FunctionClosure : Poolable {

    internal val closure: ObjectMap<String, Any> = ObjectMap()
    internal var function: KFunction<*>? = null

    fun capture(function: KFunction<*>) {

    }

    internal fun call() {

        recycle(this)
    }

    override fun reset() {
        closure.clear()
        function = null
    }
}

class FunctionClosureBuilder(
        functionClosure: FunctionClosure
) {
    fun withArg(name: String, value: Any): FunctionClosureBuilder {

        return this
    }
}