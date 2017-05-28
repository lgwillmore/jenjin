package com.binarymonks.jj.core.specs.render

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.assets.AssetReference
import com.binarymonks.jj.core.render.nodes.RenderNode
import com.binarymonks.jj.core.render.nodes.SimpleTextureProvider
import com.binarymonks.jj.core.render.nodes.TextureProvider
import com.binarymonks.jj.core.render.nodes.TextureRenderNode
import com.binarymonks.jj.core.properties.PropDelegate
import com.binarymonks.jj.core.workshop.ParamStack
import kotlin.reflect.KClass

abstract class RenderNodeSpec {
    var layer: Int = 0
    var priority: Int = 0
    var color: PropDelegate<Color> = PropDelegate(Color.WHITE)
    abstract fun getAssets(): Array<AssetReference>
    abstract fun makeNode(paramsStack: ParamStack): RenderNode
}

abstract class SpatialRenderNodeSpec : RenderNodeSpec() {
    var offsetX = 0f
    var offsetY = 0f
    var rotationD = 0f
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
        val textureProvider: TextureProvider = if (assetPath == null) {
            throw Exception("No Path set in backing image")
        } else {
            SimpleTextureProvider(JJ.B.assets.getAsset(assetPath!!, Texture::class))
        }
        return TextureRenderNode(
                priority = priority,
                color = color.copy(),
                provider = textureProvider,
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