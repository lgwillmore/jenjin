package com.binarymonks.jj.core.render.nodes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.binarymonks.jj.core.properties.PropOverride
import com.binarymonks.jj.core.specs.render.GraphID
import com.binarymonks.jj.core.things.Thing


abstract class RenderNode(
        var priority: Int = 0,
        var color: PropOverride<Color>,
        var graphID: GraphID,
        var name: String? = null
) {
    var parent: Thing? = null

    fun myParent(): Thing {
        if (parent == null) {
            throw Exception("parent has not been set")
        }
        return parent!!
    }

    abstract fun render(camera: OrthographicCamera)

    abstract fun dispose()
}