package com.binarymonks.jj.core.spine.specs

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.assets.AssetReference
import com.binarymonks.jj.core.spine.components.SPINE_RENDER_NAME
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.render.nodes.RenderNode
import com.binarymonks.jj.core.spine.render.SpineRenderNode
import com.binarymonks.jj.core.specs.render.RenderNodeSpec
import com.binarymonks.jj.core.spine.RagDollBone
import com.binarymonks.jj.core.workshop.ParamStack
import com.esotericsoftware.spine.Bone
import com.esotericsoftware.spine.BoneData
import com.esotericsoftware.spine.Skeleton
import com.esotericsoftware.spine.SkeletonJson
import com.esotericsoftware.spine.attachments.AtlasAttachmentLoader


internal class SpineRenderNodeSpec(
        var atlasPath: String? = null,
        var dataPath: String? = null,
        var originX: Float = 0f,
        var originY: Float = 0f,
        var scale: Float = 1f
) : com.binarymonks.jj.core.specs.render.RenderNodeSpec() {

    /**
     * A builder constructor
     */
    constructor(
            atlasPath: String? = null,
            dataPath: String? = null,
            originX: Float = 0f,
            originY: Float = 0f,
            scale: Float = 1f,
            build: com.binarymonks.jj.core.spine.specs.SpineRenderNodeSpec.() -> Unit
    ) : this(atlasPath, dataPath, originX, originY, scale) {
        this.build()
    }

    override fun getAssets(): com.badlogic.gdx.utils.Array<AssetReference> {
        return com.badlogic.gdx.utils.Array.with(com.binarymonks.jj.core.assets.AssetReference(com.badlogic.gdx.graphics.g2d.TextureAtlas::class, checkNotNull(atlasPath)))
    }

    override fun makeNode(paramsStack: com.binarymonks.jj.core.workshop.ParamStack): com.binarymonks.jj.core.render.nodes.RenderNode {
        val atlas = com.binarymonks.jj.core.JJ.assets.getAsset(checkNotNull(atlasPath), com.badlogic.gdx.graphics.g2d.TextureAtlas::class)


        val atlasLoader = com.esotericsoftware.spine.attachments.AtlasAttachmentLoader(atlas)
        val json = com.esotericsoftware.spine.SkeletonJson(atlasLoader)
        val actualScale = scale * paramsStack.scaleX
        json.scale = actualScale
        val skeletonData = json.readSkeletonData(com.badlogic.gdx.Gdx.files.internal(dataPath))
        val positionOffset = com.binarymonks.jj.core.pools.vec2()
        positionOffset.set(originX, originY).scl(scale * paramsStack.scaleX)
        val skeleton = com.esotericsoftware.spine.Skeleton(skeletonData)

        val spineNode = com.binarymonks.jj.core.spine.render.SpineRenderNode(
                priority,
                color,
                graphID,
                SPINE_RENDER_NAME,
                skeleton,
                skeletonData,
                positionOffset
        )
        return spineNode
    }

    internal fun getRagDollBone(boneData: com.esotericsoftware.spine.BoneData, skeleton: com.esotericsoftware.spine.Skeleton, parent: com.esotericsoftware.spine.Bone): RagDollBone {
        return RagDollBone(boneData, skeleton, parent)
    }

}