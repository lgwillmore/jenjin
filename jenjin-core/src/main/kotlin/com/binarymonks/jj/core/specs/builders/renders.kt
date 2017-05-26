package com.binarymonks.jj.core.specs.builders

import com.binarymonks.jj.core.specs.render.RenderSpec
import com.binarymonks.jj.core.specs.render.TextureRenderNodeSpec


fun RenderSpec.texture(init: TextureRenderNodeSpec.() -> Unit) {
    val textureRenderSpec: TextureRenderNodeSpec = TextureRenderNodeSpec()
    textureRenderSpec.init()
    this.renderNodes.add(textureRenderSpec)
}


