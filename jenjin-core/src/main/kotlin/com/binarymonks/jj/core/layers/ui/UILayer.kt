package com.binarymonks.jj.core.layers.ui

import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ObjectMap
import com.badlogic.gdx.utils.viewport.Viewport
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.layers.Layer
import com.binarymonks.jj.core.layers.LayerStack
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.scenes.Scene


open class UILayer(
        override var inputMultiplexer: InputMultiplexer = InputMultiplexer(),
        override var stack: LayerStack? = null
) : Stage(), Layer {

    val namedActors: ObjectMap<String, Actor> = ObjectMap()

    init {
        this.inputMultiplexer.addProcessor(this)
    }

    constructor(
            viewport: Viewport
    ) : this(inputMultiplexer = InputMultiplexer(), stack = null) {
        this.viewport = viewport
    }

    fun addActor(name: String, actor: Actor) {
        super.addActor(actor)
        namedActors.put(name, actor)
    }

    fun getActor(name: String): Actor {
        return namedActors.get(name)
    }

    override fun update() {
        act(JJ.clock.deltaFloat)
        draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }
}

class SceneAwareActorProxy: Actor(){
    val offset = new(Vector2::class)
    var scene: Scene? = null
    var innerActor: Actor? = null

    override fun act(delta: Float) {
        innerActor!!.act(delta)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        val scenePosition = scene!!.physicsRoot.position()
        scenePosition.add(offset)
        super.draw(batch, parentAlpha)
    }
}