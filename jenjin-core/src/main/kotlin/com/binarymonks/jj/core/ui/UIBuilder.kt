package com.binarymonks.jj.core.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.binarymonks.jj.core.layers.Layer

class UIBuilder() {

    val layer: UILayer = UILayer()

    constructor(build: UIBuilder.() -> Unit) : this() {
        this.build()
    }

    fun <T : Actor> actor(name: String? = null, actor: T, build: (T.() -> Unit)? = null): ActorListenerAppender {
        if (build != null) {
            actor.build()
        }
        if (name != null) {
            layer.addActor(name, actor)
        } else {
            layer.addActor(actor)
        }
        return ActorListenerAppender(layer, actor)
    }

    fun build(): Layer {
        return layer
    }

}

class ActorListenerAppender(private val uiLayer: UILayer, private val actor: Actor) {

    fun <T : LayerAwareListener> withListener(listener: T): ActorListenerAppender {
        listener.myActor = actor
        listener.uiLayer = uiLayer
        return this
    }

}