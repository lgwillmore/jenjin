package com.binarymonks.jj.core.specs.render

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.assets.AssetReference
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.recycleItems
import com.binarymonks.jj.core.properties.PropOverride
import com.binarymonks.jj.core.render.nodes.*
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.ShapeSpec
import com.binarymonks.jj.core.workshop.ParamStack
import kotlin.reflect.KClass

abstract class RenderNodeSpec {
    var id = JJ.B.nextID()
    var layer: Int = 0
    var priority: Int = 0
    var color: PropOverride<Color> = PropOverride(Color.WHITE)
    abstract fun getAssets(): Array<AssetReference>
    abstract fun makeNode(paramsStack: ParamStack): RenderNode
}


abstract class SpatialRenderNodeSpec : RenderNodeSpec() {
    var offsetX = 0f
    var offsetY = 0f
    var rotationD = 0f
}

class ShapeNodeSpec : SpatialRenderNodeSpec() {
    var shape: ShapeSpec = Rectangle()
    override fun makeNode(paramsStack: ParamStack): RenderNode {
        val shape = this.shape
        when (shape) {
            is Rectangle -> return makeRectangle(shape, paramsStack)
            else -> {
                throw Exception("Unknown shape: ${shape::class.simpleName}")
            }
        }
    }

    private fun makeRectangle(shape: Rectangle, paramsStack: ParamStack): RenderNode {
        if (PolygonRenderNode.haveBuilt(this)) {
            return PolygonRenderNode.rebuild(this, paramsStack.peek())
        } else {
            val points = Array<Vector2>()
            points.add(new(Vector2::class).set(-shape.width / 2, -shape.height / 2))
            points.add(new(Vector2::class).set(shape.width / 2, -shape.height / 2))
            points.add(new(Vector2::class).set(shape.width / 2, shape.height / 2))
            points.add(new(Vector2::class).set(-shape.width / 2, shape.height / 2))
            return PolygonRenderNode.buildNew(
                    this,
                    points,
                    new(Vector2::class).set(offsetX, offsetY),
                    rotationD,
                    paramsStack.scaleX,
                    paramsStack.scaleY
            )
            recycleItems(points)
        }
    }

    override fun getAssets(): Array<AssetReference> {
        return Array()
    }
}

abstract class ImageNodeSpec<out K : KClass<*>> : SpatialRenderNodeSpec() {
    var width: Float = 1f
    var height: Float = 1f
    var assetPath: String? = null

    override fun getAssets(): Array<AssetReference> {
        val assets: Array<AssetReference> = Array()
        if (assetPath != null) {
            assets.add(AssetReference(assetClass(), assetPath!!))
        } else {
            throw Exception("Trying to fetch an asset that has no path set")
        }
        return assets
    }

    open abstract fun assetClass(): K
}

class TextureNodeSpec : ImageNodeSpec<KClass<Texture>>() {

    override fun assetClass() = Texture::class

    override fun makeNode(paramsStack: ParamStack): RenderNode {
        val frameProvider: FrameProvider = if (assetPath == null) {
            throw Exception("No Path set in backing image")
        } else {
            TextureFrameProvider(JJ.B.assets.getAsset(assetPath!!, Texture::class))
        }
        return TextureRenderNode(
                priority = priority,
                color = color.copy(),
                provider = frameProvider,
                offsetX = offsetX,
                offsetY = offsetY,
                rotationD = rotationD,
                width = width,
                height = height,
                scaleX = paramsStack.scaleX,
                scaleY = paramsStack.scaleY
        )

    }
}