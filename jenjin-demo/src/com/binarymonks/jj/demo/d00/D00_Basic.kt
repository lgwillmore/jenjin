package com.binarymonks.jj.demo.d00

import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.physics.Circle
import com.binarymonks.jj.core.specs.physics.Polygon
import com.binarymonks.jj.core.specs.physics.Rectangle


/**
 * The simplest of the simple
 *
 * Create some scenes and instantiate them.
 *
 * Uses physics rendering
 */
class D00_Basic(jjConfig: JJConfig) : Game(jjConfig) {

    public override fun gameOn() {

        // Scenes we can use again by referring to the path
        JJ.scenes.addSceneSpec("square", square())
        JJ.scenes.addSceneSpec("circle", circle())
        JJ.scenes.addSceneSpec("floor", floor())

        // A composite scene
        val initialSceneSpec = scene {
            nodeRef(params { x = 4f; y = 8f }) { "circle" }
            nodeRef(params { x = 8f; y = 8f }) { "circle" }
            nodeRef(params { x = 12f; y = 8f }) { "circle" }
            nodeRef(params { x = 16f; y = 8f }) { "circle" }
            nodeRef(params { x = 4f; y = 5f; rotationD = 45f }) { "square" }
            nodeRef(params { x = 16f; y = 5f; rotationD = 45f }) { "square" }
            node(params { x = 10f; y = 6f }) {
                thing {
                    physics {
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            shape = Polygon(
                                    vec2(0f, 1f),
                                    vec2(-1f, 0f),
                                    vec2(1f, 0f)
                            )
                        }
                    }
                }
            }
            nodeRef(params { x = 0f; y = 0f }) { "floor" }
        }

        // And then we instantiate some scenes
        JJ.scenes.instantiate(initialSceneSpec).then({ println("Scene Loaded") })
    }

    fun onLoad() {
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

    private fun floor(): SceneSpec {
        // A static floor object
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                    fixture {
                        shape = Rectangle(width = 20f, height = 1f)
                    }
                }
            }
        }
    }
}
