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

/**
 * Merges a map into this map. Keys will be overriden by the mergeFrom map.
 *
 * Returns the original
 */
fun <K, V> ObjectMap<K, V>.merge(mergeFrom: ObjectMap<K, V>): ObjectMap<K, V> {
    for (entry in mergeFrom) {
        this.put(entry.key, entry.value)
    }
    return this
}


fun <T> Array<T>.addVar(vararg add: T): Array<T> {
    add.forEach { this.add(it) }
    return this
}

fun <T> Array<T>.copy(): Array<T> {
    val array = Array<T>()
    this.forEach {
        array.add(it)
    }
    return array
}


