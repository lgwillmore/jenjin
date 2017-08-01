package com.binarymonks.jj.core.components.fsm

import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.scenes.Scene


abstract class State : Component() {


    /**
     * Called when the state is entered
     */
    open fun enter() {}

    /**
     * Called when the state is exited
     */
    open fun exit() {}

}


class CompositeState : State() {

    internal var states: Array<State> = Array()

    override var scene: Scene?
        get() = super.scene
        set(value) {
            super.scene = value
            states.forEach { it.scene = value }
        }

    override fun onAddToScene() {
        states.forEach { it.onAddToScene() }
    }

    override fun onAddToWorld() {
        states.forEach { it.onAddToWorld() }
    }

    override fun onRemoveFromScene() {
        states.forEach { it.onRemoveFromScene() }
    }

    override fun onRemoveFromWorld() {
        states.forEach { it.onRemoveFromWorld() }
    }

    fun <T : State> part(component: T, build: (T.() -> Unit)? = null) {
        if (build != null)
            component.build()
        states.add(component)
    }

}