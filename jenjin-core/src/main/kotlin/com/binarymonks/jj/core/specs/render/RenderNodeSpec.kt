package com.binarymonks.jj.core.specs.render

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.PolygonSprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.assets.AssetReference
import com.binarymonks.jj.core.extensions.copy
import com.binarymonks.jj.core.pools.recycleItems
import com.binarymonks.jj.core.properties.PropOverride
import com.binarymonks.jj.core.render.nodes.*
import com.binarymonks.jj.core.workshop.ParamStack
import kotlin.reflect.KClass

abstract class RenderNodeSpec {
    var id = JJ.B.nextID()
    var layer: Int = 0
    var priority: Int = 0
    var color: PropOverride<Color> = PropOverride(Color.WHITE)
    var graphID: GraphID = DefaultGraph()
    var name:String? = null
    abstract fun getAssets(): Array<AssetReference>
    abstract fun makeNode(paramsStack: ParamStack): RenderNode
}


abstract class SpatialRenderNodeSpec : RenderNodeSpec() {
    var offsetX = 0f
    var offsetY = 0f
    var rotationD = 0f
}

class PolygonRenderNodeSpec : SpatialRenderNodeSpec() {
    var vertices: Array<Vector2> = Array()

    override fun makeNode(paramsStack: ParamStack): RenderNode {
        var sprite: PolygonSprite? = null
        if (PolygonRenderNode.haveBuilt(this)) {
            sprite = PolygonRenderNode.getSprite(this)
        } else {
            val vertCopy: Array<Vector2> = Array()
            vertices.forEach { vertCopy.add(it.copy()) }
            sprite = PolygonRenderNode.polygonSprite(vertices)
            recycleItems(vertCopy)
        }
        return PolygonRenderNode(
                priority,
                color.copy(),
                graphID,
                checkNotNull(sprite),
                paramsStack.scaleX,
                paramsStack.scaleY,
                offsetX * paramsStack.scaleX,
                offsetY * paramsStack.scaleY,
                rotationD
        )
    }

    override fun getAssets(): Array<AssetReference> {
        return Array()
    }
}

class LineChainRenderNodeSpec : SpatialRenderNodeSpec() {
    var vertices: Array<Vector2> = Array()
    var fill: Boolean = false

    override fun makeNode(paramsStack: ParamStack): RenderNode {
        val vertCopy: Array<Vector2> = Array()
        vertices.forEach { vertCopy.add(it.copy()) }
        return LineChainRenderNode(
                priority,
                color.copy(),
                graphID,
                fill,
                paramsStack.scaleX,
                paramsStack.scaleY,
                offsetX,
                offsetY,
                rotationD,
                vertCopy
        )
    }

    override fun getAssets(): Array<AssetReference> {
        return Array()
    }

}

class CircleRenderNodeSpec : SpatialRenderNodeSpec() {
    var radius: Float = 1f
    var fill: Boolean = true

    override fun makeNode(paramsStack: ParamStack): RenderNode {
        return CircleRenderNode(
                this.priority,
                this.color.copy(),
                graphID,
                fill = fill,
                offsetX = offsetX*paramsStack.scaleX,
                offsetY = offsetY*paramsStack.scaleY,
                radius = radius * paramsStack.scaleX
        )
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
        return FrameRenderNode(
                priority = priority,
                color = color.copy(),
                graphID = graphID,
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