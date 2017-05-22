package com.binarymonks.jj.core.layers


import com.badlogic.gdx.InputMultiplexer

abstract class Layer {

    var inputMultiplexer = InputMultiplexer()
    var stack: LayerStack? = null

    abstract fun update()

    fun removeSelf() {
        if (stack != null) {
            stack!!.remove(this)
        }
    }
}
