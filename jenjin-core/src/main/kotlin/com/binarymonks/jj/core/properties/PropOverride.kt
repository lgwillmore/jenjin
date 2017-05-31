package com.binarymonks.jj.core.properties


/**
 * Used to indicate fields that can be set by value or by looking up a property of the runtime parent.
 *
 * If [setOverride] is used, this will take precedence  if the property exists. If not the Value will be used
 * as a fallback. If [set] is called it will cancel the property override, and the value will take precedence.
 */
class PropOverride<T>(value: T) {

    private var value: T = value
        private set

    private var propOverrideKey: String? = null
        private set

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
    fun get(): T {
        if (propOverrideKey != null && hasProps != null && hasProps!!.hasProp(propOverrideKey!!)) {
            return hasProps!!.getProp(propOverrideKey!!) as T
        }
        return value
    }

    fun copy(): PropOverride<T> {
        val clone = PropOverride(value)
        clone.propOverrideKey = propOverrideKey
        return clone
    }

    fun copyFrom(original: PropOverride<T>) {
        this.propOverrideKey = original.propOverrideKey
        this.value = original.value
    }


}


