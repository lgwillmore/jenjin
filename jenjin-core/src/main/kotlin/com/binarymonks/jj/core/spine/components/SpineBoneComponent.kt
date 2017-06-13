package com.binarymonks.jj.core.spine.components

import com.badlogic.gdx.math.MathUtils
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.spine.RagDollBone

class SpineBoneComponent : Component() {

    internal var spineParent: SpineComponent? = null
    internal var bone: RagDollBone? = null
    internal var ragDoll = false

    override fun update() {
        updatePosition()
    }

    fun updatePosition() {
        if (!ragDoll) {
            val x = bone!!.worldX
            val y = bone!!.worldY
            val rotation = bone!!.worldRotationX
            thing().physicsRoot.b2DBody.setTransform(x, y, rotation * MathUtils.degRad)
        }
    }

    fun triggerRagDoll() {
//        if (!ragDoll) {
//            ragDoll = true
//            bone.triggerRagDoll()
//            parent.physicsRoot.b2DBody.type = BodyDef.BodyType.DynamicBody
//            spineParent!!.triggerRag
//        }
    }

    fun setBone(bone: RagDollBone) {
        bone.spinePart = this
        this.bone = bone
    }

    fun reverseRagDoll() {
//        if (ragDoll) {
//            ragDoll = false
//            bone.reverseRagDoll()
//            thing().physicsRoot.b2DBody.setType(BodyDef.BodyType.StaticBody)
//            spineParent!!.reverseRagDoll()
//        }
    }
}
