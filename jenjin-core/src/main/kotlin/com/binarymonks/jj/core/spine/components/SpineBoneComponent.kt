package com.binarymonks.jj.core.spine.components

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.recycle
import com.binarymonks.jj.core.scenes.ScenePath
import com.binarymonks.jj.core.spine.RagDollBone
import com.binarymonks.jj.core.spine.render.SpineRenderNode
import com.binarymonks.jj.core.scenes.Scene
import com.esotericsoftware.spine.Bone

class SpineBoneComponent : Component() {

    internal var spineParent: SpineComponent? = null
    internal var bone: RagDollBone? = null
    internal var ragDoll = false
    var bonePath: Array<String> = Array()

    override fun onAddToWorld() {
        val spineParentThing = getRootNode()
        spineParent = spineParentThing.getComponent(SpineComponent::class)
        val spineRender: SpineRenderNode = spineParentThing.renderRoot.getNode(SPINE_RENDER_NAME) as SpineRenderNode
        val boneNode = findMyBone(spineRender.skeleton.rootBone, 0) as RagDollBone
        setBone(boneNode)
        spineParent!!.addBone(bone!!.data.name, this)
    }

    private fun findMyBone(boneNode: Bone?, offset: Int): Bone {
        if (boneNode == null) {
            throw Exception("Could not find bone")
        }
        if (offset+1 == bonePath.size) {
            return boneNode
        }
        boneNode.children.forEach {
            if (it.data.name == bonePath[offset+1]) {
                return findMyBone(it, offset + 1)
            }
        }
        throw Exception("Could not find bone ${bonePath[offset]}")
    }

    private fun getRootNode(): Scene {
        val scenePath: ScenePath = new(ScenePath::class)
        for(i in 0..bonePath.size-1){
            scenePath.up()
        }
        val root = me().getNode(scenePath)
        recycle(scenePath)
        return root
    }

    override fun update() {
        updatePosition()
    }

    fun updatePosition() {
        if (!ragDoll) {
            val x = bone!!.worldX
            val y = bone!!.worldY
            val rotation = bone!!.worldRotationX
            me().physicsRoot.b2DBody.setTransform(x, y, rotation * MathUtils.degRad)
        }
    }

    fun triggerRagDoll() {
        if (!ragDoll) {
            ragDoll = true
            bone!!.triggerRagDoll()
            scene!!.physicsRoot.b2DBody.type = BodyDef.BodyType.DynamicBody
            spineParent!!.triggerRagDoll()
        }
    }

    fun setBone(bone: RagDollBone) {
        bone.spinePart = this
        this.bone = bone
    }

    fun reverseRagDoll() {
        if (ragDoll) {
            ragDoll = false
            bone!!.reverseRagDoll()
            me().physicsRoot.b2DBody.type = BodyDef.BodyType.StaticBody
            spineParent!!.reverseRagDoll()
        }
    }


}
