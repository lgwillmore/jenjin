package com.binarymonks.jj.core.extensions

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.badlogic.gdx.utils.ObjectSet
import com.binarymonks.jj.core.Copyable
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.forceCopy
import com.binarymonks.jj.core.pools.newArray
import com.binarymonks.jj.core.pools.newObjectMap
import com.binarymonks.jj.core.pools.recycle

/**
 * Copies the map with [Copyable] awareness for values. Uses pooled Object maps via [newObjectMap]
 */
fun <K, V> ObjectMap<K, V>.copy(): ObjectMap<K, V> {
    val clone: ObjectMap<K, V> = newObjectMap()
    @Suppress("UNCHECKED_CAST")
    for (entry in this) {
        val value = entry.value
        val valueCopy = forceCopy(value) as V
        clone.put(entry.key, valueCopy)
    }
    return clone
}

fun <K, V> ObjectMap<K, V>.recycle() {
    JJ.B.pools.recycle(this)
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

/**
 * Lets you build your maps entries
 */
fun <K, V> ObjectMap<K, V>.build(builder: ObjectMap<K, V>.() -> Unit): ObjectMap<K, V> {
    this.builder()
    return this
}


/**
 * Copies the array with [Copyable] awareness for values
 */
fun <T> Array<T>.copy(): Array<T> {
    val clone: Array<T> = Array()
    @Suppress("UNCHECKED_CAST")
    this.forEach {
        val valueCopy = forceCopy(it) as T
        clone.add(valueCopy)
    }
    return clone
}

/**
 * Lets you build your array
 */
fun <T> Array<T>.build(builder: Array<T>.() -> Unit): Array<T> {
    this.builder()
    return this
}

fun <T> Array<T>.addVar(vararg add: T): Array<T> {
    add.forEach { this.add(it) }
    return this
}


fun <T> Array<T>.addAll(objectSet: ObjectSet<T>): Array<T> {
    objectSet.forEach { this.add(it) }
    return this
}

/**
 * Copies the set with [Copyable] awareness for values
 */
fun <T> ObjectSet<T>.copy(): ObjectSet<T> {
    val clone: ObjectSet<T> = ObjectSet()
    @Suppress("UNCHECKED_CAST")
    this.forEach {
        val valueCopy = forceCopy(it) as T
        clone.add(valueCopy)
    }
    return clone
}

fun <T> ObjectSet<T>.addVar(vararg add: T): ObjectSet<T> {
    add.forEach { this.add(it) }
    return this
}


private val emptyArraySingleton: Array<*> = Array<Any>()

@Suppress("UNCHECKED_CAST")
fun <T> emptyGDXArray(): Array<T> {
    return emptyArraySingleton as Array<T>
}


fun <T> ObjectSet<T>.randomMembers(numberToSelect: Int, selected: ObjectSet<T>) {
    if (numberToSelect > this.size || numberToSelect < 0) {
        throw Exception("Cannot select $numberToSelect from ${this.size}")
    }
    val pool: Array<T> = newArray()
    pool.addAll(this)
    val originalSize = selected.size
    while ((selected.size - originalSize) < numberToSelect) {
        selected.add(pool.removeIndex(MathUtils.random(0, pool.size - 1)))
    }
    recycle(pool)
}

fun <T> Array<T>.randomMembers(numberToSelect: Int, selected: Array<T>) {
    if (numberToSelect > this.size || numberToSelect < 0) {
        throw Exception("Cannot select $numberToSelect from ${this.size}")
    }
    val pool: Array<T> = newArray()
    pool.addAll(this)
    val originalSize = selected.size
    while ((selected.size - originalSize) < numberToSelect) {
        selected.add(pool.removeIndex(MathUtils.random(0, pool.size - 1)))
    }
    recycle(pool)
}



