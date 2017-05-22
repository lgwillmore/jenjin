package com.binarymonks.jj.core.api


import com.binarymonks.jj.core.layers.Layer

interface LayersAPI {
    fun <T> addLayerTop(add: Layer)
}