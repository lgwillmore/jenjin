package com.binarymonks.jj.core

import com.binarymonks.jj.core.api.LayersAPI
import com.binarymonks.jj.core.api.ScenesAPI
import com.binarymonks.jj.core.layers.Layer
import com.binarymonks.jj.core.layers.LayerStack
import com.binarymonks.jj.core.pools.Pools
import com.binarymonks.jj.core.render.RenderWorld
import com.binarymonks.jj.core.scenes.Scenes

/**
 * The front end global api.
 *
 * Provides access to the commonly used interfaces and operations for interacting
 * with the engine. For complete interfaces have a look at [JJ.B]
 *
 */
object JJ {
    lateinit var scenes: ScenesAPI
    lateinit var layers: LayersAPI
    var pools: Pools = Pools()

    lateinit var B: Backend

    fun initialise(config: JJConfig) {
        B = Backend(
                Scenes(),
                LayerStack(),
                RenderWorld()
        )
        scenes = B.scenes
        layers = B.layers
    }
}