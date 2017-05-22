package com.binarymonks.jj.core.layers


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.api.LayersAPI

class LayerStack : LayersAPI {

    private var inputMultiplexer = InputMultiplexer()
    private  var clearColor = Color(0f, 0f, 0f, 1f)
    private  var layers = Array<Layer>()

    init {
        Gdx.input.inputProcessor = inputMultiplexer
    }

    fun update() {
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        for (layer in layers) {
            layer.update()
        }
    }

    override fun addLayerTop(layer: Layer) {
        layers.insert(layers.size, layer)
        layer.stack = this
        inputMultiplexer.addProcessor(0, layer.inputMultiplexer)
    }

    fun remove(layer: Layer) {
        layers.removeValue(layer, true)
    }
}
