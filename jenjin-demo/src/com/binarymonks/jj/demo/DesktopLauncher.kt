package com.binarymonks.jj.demo

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.binarymonks.jj.demo.demos.D09_spine
import com.binarymonks.jj.demo.demos.D12_shaders
import com.binarymonks.jj.demo.demos.Pong

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val lwjglConfig = LwjglApplicationConfiguration()
        lwjglConfig.height = 1000
        lwjglConfig.width = 1000

        //Swap out the various demo Games here
        LwjglApplication(D12_shaders(), lwjglConfig)
    }
}
