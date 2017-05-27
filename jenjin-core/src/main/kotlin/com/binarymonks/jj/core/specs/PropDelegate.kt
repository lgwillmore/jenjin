package com.binarymonks.jj.core.specs


/**
 * Used to indicate fields that can be set by value or by looking up a property of the runtime parent
 * [com.binarymonks.jj.core.things.Thing]
 */
class PropDelegate<T>(value: T) {

    private var value: T = value
        private set

    private var propRefKey: String? = null
        private set

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
    fun resolve(): T{
       return value
    }

    fun copy():PropDelegate<T>{
        val clone = PropDelegate(value)
        clone.propRefKey = propRefKey
        return clone
    }


}


