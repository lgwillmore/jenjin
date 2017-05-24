package com.binarymonks.jj.demo.d01

import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.physics.Circle


class D01_nested_transforms : Game(MyConfig.jjConfig) {

    public override fun gameOn() {

        // Scenes we can use again by referring to the path
        JJ.scenes.addSceneSpec("circle", circle())
        JJ.scenes.addSceneSpec("triangle", circle())

        /*
        A composite Scene. This scene is using deeply nested nodes.

        The nodes transforms (translation, rotation, scale) operate in the space of the parent.
        All the way up to the global b2d world space.
         */
        val nestedParams = params { x = 4f; y = 4f; scaleX = 0.5f; scaleY = 0.5f; rotationD = 45f }

        val initialSceneSpec = scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                    fixture {
                        shape = Circle(4f)
                    }
                }
            }
            node (nestedParams){
                thing {
                    physics {
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            shape = Circle(4f)
                        }
                    }
                }
                node(nestedParams) {
                    thing {
                        physics {
                            bodyType = BodyDef.BodyType.StaticBody
                            fixture {
                                shape = Circle(4f)
                            }
                        }
                    }
                    node(nestedParams) {
                        thing {
                            physics {
                                bodyType = BodyDef.BodyType.StaticBody
                                fixture {
                                    shape = Circle(4f)
                                }
                            }
                        }
                        node(nestedParams) {
                            thing {
                                physics {
                                    bodyType = BodyDef.BodyType.StaticBody
                                    fixture {
                                        shape = Circle(4f)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // And then we instantiate some scenes
        println("Instantiating")
        JJ.scenes.instantiate(initialSceneSpec).then({ println("Scene Loaded") })
    }

    fun onLoad() {
        println("Scene Loaded")
    }

    private fun circle(): SceneSpec {
        // A bouncy ball with mostly default settings
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                    fixture {
                        shape = Circle()
                        restitution = 0.7f
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