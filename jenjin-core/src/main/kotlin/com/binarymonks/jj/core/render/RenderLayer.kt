package com.binarymonks.jj.core.render

import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.render.nodes.RenderNode

private var nodeCompare: Comparator<RenderNode> =  Comparator { renderNode1: RenderNode, renderNode2: RenderNode -> renderNode1.priority - renderNode2.priority}

class RenderLayer {
    var layer: Int = 0
    var renderNodes: Array<RenderNode> = Array()
        private set

    fun add(node: RenderNode) {
        renderNodes.add(node)
        renderNodes.sort(nodeCompare)
    }


}