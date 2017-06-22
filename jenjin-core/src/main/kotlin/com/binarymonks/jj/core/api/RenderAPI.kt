package com.binarymonks.jj.core.api


interface RenderAPI {

    //TODO: This needs to hook into the game rendering layer, not the Renderworld. Other layers might not want the same ambient light
    fun setAmbientLight(r: Float, g: Float, b: Float, a: Float)

}