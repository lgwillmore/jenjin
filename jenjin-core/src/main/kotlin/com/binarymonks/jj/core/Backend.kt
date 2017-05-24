package com.binarymonks.jj.core

import com.binarymonks.jj.core.layers.LayerStack
import com.binarymonks.jj.core.physics.PhysicsWorld
import com.binarymonks.jj.core.pools.Pools
import com.binarymonks.jj.core.render.RenderWorld
import com.binarymonks.jj.core.scenes.Scenes
import com.binarymonks.jj.core.time.TimeControls

/**
 * The Backend of the [JJ] api. Gives access to full interfaces.
 */
class Backend{
    lateinit var config: JJConfig
    lateinit var time: TimeControls
    lateinit var scenes: Scenes
    lateinit var layers: LayerStack
    lateinit var renderWorld: RenderWorld
    lateinit var physicsWorld: PhysicsWorld
    lateinit var pools: Pools
}