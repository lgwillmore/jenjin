package com.binarymonks.jj.core

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.binarymonks.jj.core.assets.Assets
import com.binarymonks.jj.core.audio.Audio
import com.binarymonks.jj.core.layers.LayerStack
import com.binarymonks.jj.core.physics.PhysicsWorld
import com.binarymonks.jj.core.pools.Pools
import com.binarymonks.jj.core.render.RenderWorld
import com.binarymonks.jj.core.scenes.Scenes
import com.binarymonks.jj.core.scenes.SceneWorld
import com.binarymonks.jj.core.time.ClockControls
import org.mockito.Mockito


fun mockoutGDXinJJ() {
    Gdx.input = Mockito.mock(Input::class.java)
    val config = JJConfig()
    JJ.B = Backend()
    JJ.B.config = config
    JJ.B.clock = ClockControls()
    JJ.B.scenes = Scenes()
    JJ.B.layers = LayerStack()
    JJ.B.physicsWorld = Mockito.mock(PhysicsWorld::class.java)
    JJ.B.renderWorld = Mockito.mock(RenderWorld::class.java)
    JJ.B.sceneWorld = SceneWorld()
    JJ.B.pools = Pools()
    JJ.B.assets = Assets()
    JJ.B.audio = Audio()

    JJ.scenes = JJ.B.scenes
    JJ.layers = JJ.B.layers
    JJ.pools = JJ.B.pools
    JJ.clock = JJ.B.clock
    JJ.physics = JJ.B.physicsWorld
    JJ.assets = JJ.B.assets
    JJ.render = JJ.B.renderWorld
}
