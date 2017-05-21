package com.binarymonks.jj.core.specs.render

import com.badlogic.gdx.graphics.Color
import com.binarymonks.jj.core.specs.PropDelegatable


abstract class RenderSpec {
    var name: String = ""
    var layer: Int = 0
    var priority: Int = 0
    var color: PropDelegatable<Color> = PropDelegatable(Color.WHITE)
}

class TextureRenderSpec : RenderSpec() {
    var width: Float = 0f
    var height: Float = 0f
}