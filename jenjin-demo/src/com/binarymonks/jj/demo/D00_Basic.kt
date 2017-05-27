package com.binarymonks.jj.demo

import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.physics.Circle
import com.binarymonks.jj.core.specs.physics.Polygon


/**
 * The simplest of the simple
 *
 * Create some scenes and instantiate them.
 *
 * Uses physics rendering
 */
class D00_Basic: Game(MyConfig01.jjConfig) {

    public override fun gameOn() {

        // Scenes we can use again by referring to the path
        JJ.scenes.addSceneSpec("square", square())
        JJ.scenes.addSceneSpec("circle", circle())
        JJ.scenes.addSceneSpec("floor", floor())

        // A composite scene
        val initialSceneSpec = scene {
            nodeRef(params { x = -8f; y = 8f }) { "circle" }
            nodeRef(params { x = -2f; y = 8f; scaleX = 2f; scaleY = 2f }) { "circle" }
            nodeRef(params { x = 2f; y = 8f }) { "circle" }
            nodeRef(params { x = +8f; y = 8f }) { "circle" }
            nodeRef(params { x = -6f; y = 5f; rotationD = 45f }) { "square" }
            nodeRef(params { x = +6f; y = 5f; rotationD = 45f }) { "square" }
            //Build a node in a scene
            node(params { x = 0f; y = 6f }) {
                thing {
                    physics {
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            shape = Polygon(
                                    vec2(0f, 4f),
                                    vec2(-2f, 0f),
                                    vec2(2f, 0f)
                            )
                        }
                    }
                }
            }
            nodeRef(params { x = 0f; y = 0f; scaleX = 20f }) { "floor" }
        }

        // And then we instantiate some scenes
        JJ.scenes.instantiate(initialSceneSpec)
    }

    private fun square(): SceneSpec {
        // The default values mean that we get a simple dynamic square
        return scene { thing { physics { fixture { } } } }
    }

    private fun circle(): SceneSpec {
        // A bouncy ball with mostly default settings
        return scene {
            thing {
                physics {
                    fixture {
                        shape = Circle()
                        restitution = 0.7f
                    }
                }
            }
        }
    }

    private fun floor(): SceneSpec {
        // A static floor object
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                    fixture {
                    }
                }
            }
        }
    }
}

object MyConfig00 {
    var jjConfig: JJConfig = JJConfig()
    init {
        jjConfig.b2dConfig.debug = true

        jjConfig.gameViewConfig.worldBoxWidth = 30f
        jjConfig.gameViewConfig.cameraPosX = 0f
        jjConfig.gameViewConfig.cameraPosY = 10f
    }
}

