package com.binarymonks.jj.demo

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val lwjglConfig = LwjglApplicationConfiguration()
        lwjglConfig.height = 1000
        lwjglConfig.width = 1000

        //Swap out the various demo Games here
        LwjglApplication(D08_pooling_and_destroying(), lwjglConfig)
    }
}
