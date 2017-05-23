package com.binarymonks.jj.core.specs

import com.badlogic.gdx.math.Matrix3
import com.binarymonks.jj.core.pools.PoolManager
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
    /**
     * This must be a name that is unique to its parent SceneSpec child nodes if set
     */
    var name: String? = null
    /**
     * This must be a name that is globally unique if set. It is used for retrieving
     */
    var uniqueInstanceName: String? = null

    var transformMatrix: Matrix3 = new(Matrix3::class)
        private set
        get() {
            transformMatrix.idt()
            transformMatrix.scale(scaleX, scaleY)
            transformMatrix.translate(x, y)
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
    }

    override fun create_new(): InstanceParams {
        return InstanceParams()
    }

    override fun dispose(instanceParams: InstanceParams) {

    }
}