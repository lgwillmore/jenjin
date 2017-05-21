package com.binarymonks.jj.demo.d00

import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.api.specs.InstanceParams
import com.binarymonks.jj.core.api.specs.builders.*
import com.binarymonks.jj.core.api.specs.SceneSpec


/**
 * The simplest of the simple - compose some scenes and instantiate one.
 */
class D00_Basic(jjConfig: JJConfig) : Game(jjConfig) {

    public override fun gameOn() {

        // A scene we can use again by referring to its path
        JJ.scenes.addSceneSpec("squares",squares())

        // A scene we build in place using builders
        val initialSceneSpec = scene {
            addNode(scene {

            }, InstanceParams.new())
            addNode("squares", InstanceParams.new())
        }

        // And then we instantiate the scene
        JJ.scenes.instantiate(initialSceneSpec).then(this::onLoad)
    }

    private fun onLoad(){
        println("Scene Loaded")
    }

    private fun squares(): SceneSpec {
        // A scene we build without builders
        return SceneSpec()
    }
}
