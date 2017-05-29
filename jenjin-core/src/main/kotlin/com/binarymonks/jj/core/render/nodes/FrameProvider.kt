package com.binarymonks.jj.core.render.nodes

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

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
