package com.binarymonks.jj.core.api


import com.binarymonks.jj.core.layers.Layer

interface LayersAPI {

    fun addLayerTop(add: Layer)
    fun addLayerTop(layerName: String)

    fun registerLayer(layerName: String, layer: Layer)
}