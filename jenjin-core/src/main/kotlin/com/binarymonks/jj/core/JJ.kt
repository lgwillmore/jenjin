package com.binarymonks.jj.core

import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.api.*
import com.binarymonks.jj.core.assets.Assets
import com.binarymonks.jj.core.async.Tasks
import com.binarymonks.jj.core.audio.Audio
import com.binarymonks.jj.core.events.EventBus
import com.binarymonks.jj.core.input.mapping.InputMapper
import com.binarymonks.jj.core.layers.GameRenderingLayer
import com.binarymonks.jj.core.layers.LayerStack
import com.binarymonks.jj.core.physics.PhysicsWorld
import com.binarymonks.jj.core.pools.Pools
import com.binarymonks.jj.core.render.RenderWorld
import com.binarymonks.jj.core.scenes.SceneWorld
import com.binarymonks.jj.core.scenes.Scenes
import com.binarymonks.jj.core.specs.builders.params
import com.binarymonks.jj.core.specs.builders.physics
import com.binarymonks.jj.core.specs.builders.scene
import com.binarymonks.jj.core.time.ClockControls

/**
 * The front end global api.
 *
 * Provides access to the commonly used interfaces and operations for interacting
 * with the engine. For complete interfaces have a look at [JJ.B]
 *
 */
object JJ {
    lateinit var scenes: ScenesAPI
    lateinit var assets: AssetsAPI
    lateinit var layers: LayersAPI
    lateinit var pools: PoolsAPI
    lateinit var clock: ClockAPI
    lateinit var physics: PhysicsAPI
    lateinit var render: RenderAPI
    lateinit var tasks: TasksAPI
    lateinit var input: InputAPI
    lateinit var events: EventsAPI

    lateinit var B: Backend

    internal fun initialise(config: JJConfig) {
        B = Backend()
        B.config = config
        B.clock = ClockControls()
        B.scenes = Scenes()
        B.layers = LayerStack(config.gameView.clearColor)
        B.physicsWorld = PhysicsWorld()
        B.renderWorld = RenderWorld()
        B.sceneWorld = SceneWorld()
        B.pools = Pools()
        B.assets = Assets()
        B.audio = Audio()
        B.defaultGameRenderingLayer = GameRenderingLayer(config.gameView)
        B.input = InputMapper()
        B.defaultGameRenderingLayer.inputMultiplexer.addProcessor(B.input)
        B.layers.push(B.defaultGameRenderingLayer)
        B.tasks = Tasks()
        B.defaultGameRenderingLayer.postProccessingEnabled = config.gameView.postProcessingEnabled




        scenes = B.scenes
        layers = B.layers
        pools = B.pools
        clock = B.clock
        physics = B.physicsWorld
        assets = B.assets
        render = B.renderWorld
        tasks = B.tasks
        input = B.input
        events = EventBus()

        B.scenes.instantiate(params { }, scene { physics { bodyType = BodyDef.BodyType.StaticBody } }).then({
            scene -> B.sceneWorld.rootScene = scene
            scene.onAddToWorld()
        })
    }
}