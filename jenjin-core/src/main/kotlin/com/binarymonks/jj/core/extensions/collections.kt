package com.binarymonks.jj.core.extensions

import com.badlogic.gdx.utils.ObjectMap
import com.badlogic.gdx.utils.Array


fun <K, V> ObjectMap<K, V>.copy(): ObjectMap<K, V> {
    val clone: ObjectMap<K, V> = ObjectMap()
    for (entry in this) {
        clone.put(entry.key, entry.value)
    }
    return clone
}

fun <T> Array<T>.addVar(vararg add: T): Array<T> {
    add.forEach { this.add(it) }
    return this
}


