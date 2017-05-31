package com.binarymonks.jj.core.specs

import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.pools.PoolManager
import com.binarymonks.jj.core.pools.mat3
import com.binarymonks.jj.core.pools.new


class InstanceParams internal constructor() {
    companion object Factory {
        fun new(): InstanceParams {
            return new(InstanceParams::class)
        }
    }

    var x: Float = 0f
    var y: Float = 0f
    var scaleX: Float = 1f
    var scaleY: Float = 1f
    var rotationD: Float = 0f
    var properties: ObjectMap<String, Any> = ObjectMap()
    /**
     * This must be a name that is unique to its parent SceneSpec child nodes if set
     */
    var name: String? = null
    /**
     * This must be a name that is globally unique if set. It is used for retrieving
     */
    var uniqueInstanceName: String? = null
    private var transformMatrix: Matrix3 = mat3()

    fun prop(name: String, value: Any? = null) {
        properties.put(name, value)
    }

    fun getTransformMatrix(): Matrix3 {
        transformMatrix.idt()
        transformMatrix.translate(x, y)
        transformMatrix.scale(scaleX, scaleY)
        transformMatrix.rotate(rotationD)
        return transformMatrix
    }

}

class ParamsPoolManager : PoolManager<InstanceParams> {

    override fun reset(instanceParams: InstanceParams) {
        instanceParams.x = 0f
        instanceParams.y = 0f
        instanceParams.scaleX = 1f
        instanceParams.scaleY = 1f
        instanceParams.rotationD = 0f
        instanceParams.properties.clear()

    }

    override fun create_new(): InstanceParams {
        return InstanceParams()
    }

    override fun dispose(instanceParams: InstanceParams) {

    }
}