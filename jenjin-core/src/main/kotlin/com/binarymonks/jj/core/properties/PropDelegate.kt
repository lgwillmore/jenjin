package com.binarymonks.jj.core.properties


/**
 * Used to indicate fields that can be set by value or by looking up a property of the runtime parent
 * [com.binarymonks.jj.core.things.Thing]
 */
class PropDelegate<T>(value: T) {

    private var value: T = value
        private set

    private var propRefKey: String? = null
        private set

    private var hasProps:HasProps? = null
        private get
        set

    /**
     * Set to an explicit value - do not delegate to a property
     */
    fun setToValue(value: T) {
        this.value = value
        this.propRefKey = null
    }

    /**
     * Set the key of the property to refer to - not explicit value
     */
    fun setToPropRef(propertyKey: String) {
        this.propRefKey = propertyKey
    }

    /**
     * Get the actual value dependent on property or value state
     */
    fun resolve(): T {
        if (hasProps == null && value == null) throw Exception("No properties and no value")
        if (value != null) {
            return value
        }
        return hasProps!!.fetchProps().get(checkNotNull(propRefKey)) as T
    }

    fun copy(): PropDelegate<T> {
        val clone = PropDelegate(value)
        clone.propRefKey = propRefKey
        return clone
    }

    fun copyFrom(original: PropDelegate<T>) {
        this.propRefKey = original.propRefKey
        this.value = original.value
    }


}


