package com.binarymonks.jj.core.properties

import com.binarymonks.jj.core.Copyable


/**
 * Used to indicate fields that can be set by value or by looking up a property of the runtime scene.
 *
 * If [setOverride] is used, this will take precedence  if the property exists. If not the Value will be used
 * as a fallback. If [set] is called it will cancel the property override, and the value will take precedence.
 */
class PropOverrideNullable<T>(value: T?) : Copyable<PropOverrideNullable<T>> {

    private var value: T? = value

    private var propOverrideKey: String? = null

    internal var hasProps: HasProps? = null

    /**
     * Set to an explicit value - do not delegate to a property
     */
    fun set(value: T) {
        this.value = value
        this.propOverrideKey = null
    }

    /**
     * Set the key of the property to refer to.
     *
     * Will default to the value set/initialised if no property for the key is found.
     */
    fun setOverride(propertyKey: String) {
        this.propOverrideKey = propertyKey
    }

    /**
     * Get the actual value dependent on property or value state
     */
    fun get(): T? {
        if (propOverrideKey != null && hasProps != null) {
            @Suppress("UNCHECKED_CAST")
            return hasProps!!.getProp(propOverrideKey!!) as T
        }
        return value
    }

    /**
     * Get the actual value with a specific set of properties
     */
    fun get(hasProps: HasProps): T? {
        this.hasProps = hasProps
        return get()
    }

    override fun clone(): PropOverrideNullable<T> {
        val clone = PropOverrideNullable(value)
        clone.propOverrideKey = propOverrideKey
        return clone
    }

    fun copyFrom(original: PropOverrideNullable<T>) {
        this.propOverrideKey = original.propOverrideKey
        this.value = original.value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PropOverrideNullable<*>) return false

        if (value != other.value) return false
        if (propOverrideKey != other.propOverrideKey) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value?.hashCode() ?: 0
        result = 31 * result + (propOverrideKey?.hashCode() ?: 0)
        return result
    }


}


