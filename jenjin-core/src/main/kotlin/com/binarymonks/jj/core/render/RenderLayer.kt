package com.binarymonks.jj.core.render

import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.render.nodes.RenderNode


class RenderLayer {
    var layer: Int = 0
    var renderNodes: Array<RenderNode> = Array()
}