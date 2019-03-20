package com.binarymonks.jj.core.specs

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.copy
import com.binarymonks.jj.core.pools.PoolManager
import com.binarymonks.jj.core.pools.mat3
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.properties.HasProps
import com.binarymonks.jj.core.scenes.Scene

/**
 * A [InstanceParams] builder.
 */
fun params(init: InstanceParams.() -> Unit): InstanceParams {
    val instanceParams = InstanceParams.new()
    instanceParams.init()
    return instanceParams
}

interface Params {
    var x: Float
    var y: Float
    var scaleX: Float
    var scaleY: Float
    var rotationD: Float
    var localProperties: ObjectMap<String, Any>
    /**
     * This must be a localName that is unique to its me SceneSpec child nodes if set
     */
    var localName: String?
    /**
     * This must be a localName that is globally unique if set. It is used for retrieving
     */
    var groupName: String?
}

open class ParamsBase: Params{
    override var x: Float = 0f
    override var y: Float = 0f
    override var scaleX: Float = 1f
    override var scaleY: Float = 1f
    override var rotationD: Float = 0f
    override var localProperties: ObjectMap<String, Any> = ObjectMap()
    override var localName: String? = null
    override var groupName: String? = null
}

class InstanceParams : ParamsBase(), HasProps {

    companion object Factory {
        fun new(): InstanceParams {
            return new(InstanceParams::class)
        }

        fun from(params: Params): InstanceParams{
            val new = InstanceParams()
            new.x = params.x
            new.y = params.y
            new.scaleX = params.scaleX
            new.scaleY = params.scaleY
            new.rotationD = params.rotationD
            new.localProperties = params.localProperties
            new.localName = params.localName
            new.groupName = params.groupName
            return new
        }

        fun from(rootScene: Scene): InstanceParams {
            val params = new(InstanceParams::class)
            val position = rootScene.physicsRoot.position()
            params.x = position.x
            params.y = position.y
            params.rotationD = rootScene.physicsRoot.rotationR() * MathUtils.radDeg
            return params
        }
    }

    private var transformMatrix: Matrix3 = mat3()

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun setPosition(vector: Vector2) {
        this.x = vector.x
        this.y = vector.y
    }

    fun prop(name: String, value: Any? = null) {
        localProperties.put(name, value)
    }

    fun getTransformMatrix(): Matrix3 {
        transformMatrix.idt()
        transformMatrix.translate(x, y)
        transformMatrix.scale(scaleX, scaleY)
        transformMatrix.rotate(rotationD)
        return transformMatrix
    }

    override fun hasProp(key: String): Boolean {
        return localProperties.containsKey(key)
    }

    override fun getProp(key: String): Any? {
        return localProperties[key]
    }

    fun clone(): InstanceParams {
        return copy(this)
    }

    override fun toString(): String {
        return "InstanceParams(x=$x, y=$y, scaleX=$scaleX, scaleY=$scaleY, rotationD=$rotationD, localProperties=$localProperties, localName=$localName, groupName=$groupName)"
    }
}

class ParamsPoolManager : PoolManager<InstanceParams> {

    override fun reset(pooled: InstanceParams) {
        pooled.x = 0f
        pooled.y = 0f
        pooled.scaleX = 1f
        pooled.scaleY = 1f
        pooled.rotationD = 0f
        pooled.localProperties.clear()

    }

    override fun create_new(): InstanceParams {
        return InstanceParams()
    }

    override fun dispose(pooled: InstanceParams) {

    }
}