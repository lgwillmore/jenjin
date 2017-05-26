package com.binarymonks.jj.core.render.nodes

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

interface TextureProvider {

    fun getFrame(rotationD: Float): TextureRegion

    fun dispose()
}

class SimpleTextureProvider(internal var texture: Texture) : TextureProvider {
    internal var renderFrame: TextureRegion

    init {
        this.renderFrame = TextureRegion(texture)
    }

    override fun getFrame(rotationD: Float): TextureRegion {
        return renderFrame
    }

    override fun dispose() {
        texture.dispose()
    }
}
