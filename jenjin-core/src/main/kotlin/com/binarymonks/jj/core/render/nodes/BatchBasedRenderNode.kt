package com.binarymonks.jj.core.render.nodes

import com.badlogic.gdx.graphics.Color
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.properties.PropOverride
import com.binarymonks.jj.core.specs.render.RenderGraphType


abstract class BatchBasedRenderNode(
        priority: Int,
        color: PropOverride<Color>,
        renderGraphType: RenderGraphType,
        name: String?,
        shaderPipe: String?
) : RenderNode(
        priority,
        color,
        renderGraphType,
        name,
        shaderPipe) {

    internal var batchColor: Color? = null

    override fun setShaderAndColor() {
        if (shaderPipe != null) {
            JJ.B.renderWorld.polyBatch.shader = JJ.render.getShaderPipe(shaderPipe!!)
        }
        batchColor = JJ.B.renderWorld.polyBatch.color
        JJ.B.renderWorld.polyBatch.color = color.get()
    }

    override fun restoreShaderAndColor() {
        if (shaderPipe != null) {
            JJ.B.renderWorld.polyBatch.shader = null
        }
        JJ.B.renderWorld.polyBatch.color = batchColor
    }

}