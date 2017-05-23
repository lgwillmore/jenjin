package com.binarymonks.jj.core

import com.binarymonks.jj.core.api.LayersAPI
import com.binarymonks.jj.core.api.PoolsAPI
import com.binarymonks.jj.core.api.ScenesAPI
import com.binarymonks.jj.core.api.TimeAPI
import com.binarymonks.jj.core.layers.LayerStack
import com.binarymonks.jj.core.physics.PhysicsWorld
import com.binarymonks.jj.core.pools.Pools
import com.binarymonks.jj.core.render.RenderWorld
import com.binarymonks.jj.core.scenes.Scenes
import com.binarymonks.jj.core.time.TimeControls

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
    lateinit var pools: PoolsAPI
    lateinit var time: TimeAPI

    lateinit var B: Backend

    fun initialise(config: JJConfig) {
        B = Backend(
                config,
                TimeControls(),
                Scenes(),
                LayerStack(),
                RenderWorld(),
                PhysicsWorld(),
                Pools()
        )
        scenes = B.scenes
        layers = B.layers
        pools = B.pools
        time = B.time
    }
}