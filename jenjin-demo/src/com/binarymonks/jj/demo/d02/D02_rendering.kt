package com.binarymonks.jj.demo.d02

import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*


class D02_rendering : Game(MyConfig.jjConfig) {

    public override fun gameOn() {

        // A scene with multiple render layers and internal priorities
        JJ.scenes.addSceneSpec("simpleTexture", simpleTexture())

        /*
        A composite Scene. This scene demonstrates the various ways we can draw things
         */
        val initialSceneSpec = scene {
            nodeRef(params { x = 10f;y = 6f }) { "simpleTexture" }
        }

        // And then we instantiate some scenes
        JJ.scenes.instantiate(initialSceneSpec).then({ println("Scene Loaded") })
    }

    fun onLoad() {
        println("Scene Loaded")
    }

    private fun simpleTexture(): SceneSpec {
        // The scale, rotation and translation of nested nodes happens in parents space.
        return scene {
            thing {
                render{
                 texture {
                     path="/some/thing.png";
                 }
                }
            }
        }
    }
}

object MyConfig {
    var jjConfig: JJConfig = JJConfig()

    init {
        jjConfig.b2dConfig.debug = true

        jjConfig.gameViewConfig.worldBoxWidth = 50f
        jjConfig.gameViewConfig.cameraPosX = 0f
        jjConfig.gameViewConfig.cameraPosY = 0f
    }
}