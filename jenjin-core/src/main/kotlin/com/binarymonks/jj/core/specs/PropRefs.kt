package com.binarymonks.jj.core.specs


/**
 * Used to indicate fields that can be set by value or by looking up a property of the runtime parent
 * [com.binarymonks.jj.core.things.Thing]
 */
class PropDelegatable<T>(value: T) {

    var value: T = value
        private set

    var propRefKey: String? = null
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


}


