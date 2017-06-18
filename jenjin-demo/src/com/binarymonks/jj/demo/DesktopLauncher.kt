package com.binarymonks.jj.demo

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.binarymonks.jj.demo.demos.D00_Basic
import com.binarymonks.jj.demo.demos.D07_b2d_composite
import com.binarymonks.jj.demo.demos.D10_spine_physics

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val lwjglConfig = LwjglApplicationConfiguration()
        lwjglConfig.height = 1000
        lwjglConfig.width = 1000

        //Swap out the various demo Games here
        LwjglApplication(D10_spine_physics(), lwjglConfig)
    }
}
