package com.binarymonks.jj.demo.d00

import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.api.specs.SceneSpec
import com.binarymonks.jj.core.api.specs.builders.*


/**
 * The simplest of the simple - compose some scenes and instantiate one.
 */
class D00_Basic(jjConfig: JJConfig) : Game(jjConfig) {

    public override fun gameOn() {

        // A scene we can use again by referring to its path
        JJ.scenes.addSceneSpec("square", squares())

        // A scene we build in place using builders
        val initialSceneSpec = scene {
            node(thing {
            }, params {})
            node("squares", params {})
            node(b2dscene {
                node(thing {}, params { name = "thingA" })
                node(thing {}, params { name = "thingB" })
                joint("thingA","thingB", revolute {

                })
            }, params { })
        }

        // And then we instantiate some scenes
        JJ.scenes.instantiate(initialSceneSpec).then(this::onLoad)
    }

    private fun onLoad() {
        println("Scene Loaded")
    }

    private fun squares(): SceneSpec {
        // A scene we build without builders
        return SceneSpec()
    }
}
