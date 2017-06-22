package com.binarymonks.jj.core.spine.render

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.properties.PropOverride
import com.binarymonks.jj.core.render.nodes.RenderNode
import com.binarymonks.jj.core.specs.render.RenderGraphType
import com.esotericsoftware.spine.Event
import com.esotericsoftware.spine.Skeleton
import com.esotericsoftware.spine.SkeletonData

class SpineRenderNode(
        priority: Int,
        color: PropOverride<Color>,
        renderGraphType: RenderGraphType,
        name: String? = null,
        internal var skeleton: Skeleton,
        internal var skeletonData: SkeletonData,
        internal var positionOffset: Vector2
) : RenderNode(priority, color, renderGraphType, name) {

    internal var currentAnimation: String? = null
    internal var animationTimeElapsed: Float = 0.toFloat()
    internal var events: Array<Event> = Array() //What is this?

    fun triggerAnimation(animationName: String) {
        currentAnimation = animationName
        animationTimeElapsed = 0f
    }

    override fun render(camera: OrthographicCamera) {
        animationTimeElapsed += JJ.clock.deltaFloat
        val position = parent!!.physicsRoot.position().sub(positionOffset)

        if (currentAnimation != null) {
            skeletonData.findAnimation(currentAnimation).apply(skeleton, animationTimeElapsed, animationTimeElapsed, true, events, 1f, false, false)
        }
        skeleton.setPosition(position.x, position.y)
        skeleton.updateWorldTransform()

        if (JJ.B.config.spine.render) {
            JJ.B.renderWorld.switchToBatch()
            JJ.B.renderWorld.skeletonRenderer.draw(JJ.B.renderWorld.polyBatch, skeleton)
        }

    }

    override fun dispose() {}
}
