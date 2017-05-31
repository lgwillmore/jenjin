package com.binarymonks.jj.core.render.nodes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Transform
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.properties.PropOverride

class FrameRenderNode(
        priority: Int,
        color: PropOverride<Color>,
        internal var provider: FrameProvider,
        internal var offsetX: Float,
        internal var offsetY: Float,
        internal var rotationD: Float,
        internal var width: Float,
        internal var height: Float,
        internal var scaleX: Float,
        internal var scaleY: Float) : RenderNode(priority, color) {

    override fun render(camera: OrthographicCamera) {
        var relativeRotationD = myParent().physicsRoot.rotationR() * MathUtils.radiansToDegrees + rotationD;

        var frame: TextureRegion = provider.getFrame(relativeRotationD)
        if (frame != null) {
            frame
            JJ.B.renderWorld.switchToBatch()
            JJ.B.renderWorld.switchBatchColorTo(color.get())
            val transform: Transform = myParent().physicsRoot.transform
            val position = vec2().set(offsetX, offsetY)
            transform.mul(position)
            JJ.B.renderWorld.polyBatch.draw(frame, position.x - (width / 2f), position.y - (height / 2f), width / 2f, height / 2f, width, height, scaleX, scaleY, relativeRotationD)
            JJ.B.renderWorld.switchBatchColorBack()
        }
    }

    override fun dispose() {
        provider.dispose()
    }
}

interface FrameProvider {

    fun getFrame(rotationD: Float): TextureRegion

    fun dispose()
}

class TextureFrameProvider(internal var texture: Texture) : FrameProvider {
    internal var renderFrame: TextureRegion = TextureRegion(texture)

    override fun getFrame(rotationD: Float): TextureRegion {
        return renderFrame
    }

    override fun dispose() {
        texture.dispose()
    }
}