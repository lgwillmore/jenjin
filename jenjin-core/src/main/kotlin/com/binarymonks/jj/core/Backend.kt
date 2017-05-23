package com.binarymonks.jj.core

import com.binarymonks.jj.core.layers.GameRenderingLayer
import com.binarymonks.jj.core.layers.LayerStack
import com.binarymonks.jj.core.physics.PhysicsWorld
import com.binarymonks.jj.core.pools.Pools
import com.binarymonks.jj.core.render.RenderWorld
import com.binarymonks.jj.core.scenes.Scenes
import com.binarymonks.jj.core.time.TimeControls

/**
 * The Backend of the [JJ] api. Gives access to full interfaces.
 */
class Backend(
        val config : JJConfig,
        val time : TimeControls,
        val scenes: Scenes,
        val layers: LayerStack,
        val renderWorld: RenderWorld,
        val physicsWorld: PhysicsWorld,
        val pools: Pools
){
    init {
        layers.addLayerTop(GameRenderingLayer(config.gameViewConfig))
    }
}