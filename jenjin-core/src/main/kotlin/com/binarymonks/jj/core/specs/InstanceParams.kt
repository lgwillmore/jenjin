package com.binarymonks.jj.core.specs


class InstanceParams private constructor(){
    companion object Factory {
        fun new(): InstanceParams{
            return InstanceParams()
        }
    }
    var x: Float = 0f
    var y: Float = 0f
    var scaleX: Float = 1f
    var scaleY: Float = 1f
    var rotationD: Float = 1f
    /**
     * This must be a name that is unique to its parent SceneSpec child nodes if set
     */
    var name: String? = null
    /**
     * This must be a name that is globally unique if set. It is used for retrieving
     */
    var uniqueInstanceName: String? = null
}