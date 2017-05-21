package com.binarymonks.jj.demo.d00

import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.physics.Circle


/**
 * The simplest of the simple - compose some scenes and instantiate one.
 */
class D00_Basic(jjConfig: JJConfig) : Game(jjConfig) {

    public override fun gameOn() {

        // Scenes we can use again by referring to the path
        JJ.scenes.addSceneSpec("square", square())
        JJ.scenes.addSceneSpec("circle", circle())
        JJ.scenes.addSceneSpec("floor", floor())

        // A composite scene
        val initialSceneSpec = scene {
            node { thing { } }
            node(params { x = 2f; y = 2f }) {
                nodeRef(params { name = "square1" }) { "square" }
                nodeRef(params { name = "square2" }) { "square" }
                joint("square1", "square2", revolute { })
            }
            nodeRef { "square" }
        }

        // And then we instantiate some scenes
        JJ.scenes.instantiate(initialSceneSpec).then(this::onLoad)
    }

    private fun onLoad() {
        println("Scene Loaded")
    }

    private fun square(): SceneSpec {
        // The default values mean that we get a simple dynamic square
        return scene { thing { physics { fixture { } } } }
    }

    private fun circle(): SceneSpec {
        // Sticking to default values to get a circle
        return scene { thing { physics { fixture { shape = Circle() } } } }
    }

    private fun floor(): SceneSpec{
        // A static floor object
        return scene{
            thing{
                physics{
                    bodyType=BodyDef.BodyType.StaticBody
                }
            }
        }
    }
}
