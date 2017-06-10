package com.binarymonks.jj.core.specs.spine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.assets.AssetReference
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.render.nodes.RenderNode
import com.binarymonks.jj.core.render.nodes.SpineRenderNode
import com.binarymonks.jj.core.specs.render.RenderNodeSpec
import com.binarymonks.jj.core.workshop.ParamStack
import com.esotericsoftware.spine.Skeleton
import com.esotericsoftware.spine.SkeletonJson
import com.esotericsoftware.spine.attachments.AtlasAttachmentLoader


internal class SpineRenderNodeSpec(
        var atlasPath: String? = null,
        var dataPath: String? = null,
        var originX: Float = 0f,
        var originY: Float = 0f,
        var scale: Float = 1f,
        var startingAnimation: String? = null
) : RenderNodeSpec() {


    override fun getAssets(): Array<AssetReference> {
        return Array.with(AssetReference(TextureAtlas::class, checkNotNull(atlasPath)))
    }

    override fun makeNode(paramsStack: ParamStack): RenderNode {
        val atlas = JJ.assets.getAsset(checkNotNull(atlasPath), TextureAtlas::class)


        val atlasLoader = AtlasAttachmentLoader(atlas)
        val json = SkeletonJson(atlasLoader)
        val actualScale = scale * paramsStack.scaleX
        json.scale=actualScale
        val skeletonData = json.readSkeletonData(Gdx.files.internal(dataPath))
        val positionOffset = vec2()
        positionOffset.set(originX, originY).scl(scale * paramsStack.scaleX)
        val skeleton = Skeleton(skeletonData)

        val spineNode = SpineRenderNode(
                priority,
                color,
                graphID,
                skeleton,
                skeletonData,
                positionOffset
        )
        if (startingAnimation != null) {
            spineNode.triggerAnimation(startingAnimation!!)
        }
        return spineNode
    }

}