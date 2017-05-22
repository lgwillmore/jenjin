package com.binarymonks.jj.core

import com.binarymonks.jj.core.layers.LayerStack
import com.binarymonks.jj.core.render.RenderWorld
import com.binarymonks.jj.core.scenes.Scenes

/**
 * The Backend of the [JJ] api. Gives access to full interfaces.
 */
class Backend(
        val scenes: Scenes,
        val layers: LayerStack,
        val renderWorld: RenderWorld
)