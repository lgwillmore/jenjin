package com.binarymonks.jj.core.specs.render

import com.badlogic.gdx.graphics.Color
import com.binarymonks.jj.core.specs.PropDelegate

abstract class RenderNodeSpec {
    var layer: Int = 0
    var priority: Int = 0
    var color: PropDelegate<Color> = PropDelegate(Color.WHITE)
}

abstract class SpatialRenderNodeSpec : RenderNodeSpec() {
    var offsetX = 0f
    var offsetY = 0f
    var rotationD = 0f
}

class TextureRenderNodeSpec : SpatialRenderNodeSpec() {
    var width: Float = 1f
    var height: Float = 1f
    var path: String? = null
}