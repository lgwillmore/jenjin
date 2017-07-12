package com.binarymonks.jj.core.api


import com.binarymonks.jj.core.layers.Layer

interface LayersAPI {

    fun push(add: Layer)
    fun push(layerName: String)
    fun pop()

    fun registerLayer(layerName: String, layer: Layer)
}