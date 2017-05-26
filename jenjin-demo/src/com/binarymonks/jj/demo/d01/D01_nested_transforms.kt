package com.binarymonks.jj.demo.d01

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


class D01_nested_transforms : Game(MyConfig.jjConfig) {

    public override fun gameOn() {

        // Scenes we can use again by referring to the path
        JJ.scenes.addSceneSpec("nestedCircles", nestedCircles())
        JJ.scenes.addSceneSpec("nestedRectangles", nestedRectangles())
        JJ.scenes.addSceneSpec("nestedPolygons", nestedPolygons())
        JJ.scenes.addSceneSpec("nestedImages", nestedImages())

        // We are using Images now, so we have to load the assets first
        JJ.scenes.loadAssetsNow()

        /*
        A composite Scene. This scene is using deeply nested nodes.

        The nodes transforms (translation, rotation, scale) operate in the space of the parent.
        All the way up to the global b2d world space.
         */
        val initialSceneSpec = scene {
            nodeRef(params { x = 10f;y = 6f }) { "nestedCircles" }
            nodeRef(params { x = -10f; y = 6f }) { "nestedRectangles" }
            nodeRef(params { x = 10f; y = -15f }) { "nestedPolygons" }
            nodeRef(params { x = -10f; y = -15f }) { "nestedImages" }
        }

        // And then we instantiate some scenes
        JJ.scenes.instantiate(initialSceneSpec).then({ println("Scene Loaded") })
    }


    fun onLoad() {
        println("Scene Loaded")
    }

    private fun nestedCircles(): SceneSpec {
        // The scale, rotation and translation of nested nodes happens in parents space.
        val nestedParams = params { x = 4.1f; y = 4.1f; scaleX = 0.5f; scaleY = 0.5f; rotationD = 45f }
        val nestedParams2 = params { x = -4.1f; y = 4.1f; scaleX = 0.5f; scaleY = 0.5f; rotationD = -45f }
        return scene {
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
            node(nestedParams2) {
                thing {
                    physics {
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            shape = Circle(4f)
                        }
                    }
                }
                node(nestedParams2) {
                    thing {
                        physics {
                            bodyType = BodyDef.BodyType.StaticBody
                            fixture {
                                shape = Circle(4f)
                            }
                        }
                    }
                    node(nestedParams2) {
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

    private fun nestedRectangles(): SceneSpec {
        // The scale, rotation and translation of nested nodes happens in parents space.
        val nestedParams = params { x = 4.1f; y = 4.1f; scaleX = 0.5f; scaleY = 0.75f; rotationD = 45f }
        val rectangle = Rectangle(8f, 8f)
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                    fixture {
                        shape = rectangle
                    }
                }
            }
            node(nestedParams) {
                thing {
                    physics {
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            shape = rectangle
                        }
                    }
                }
                node(nestedParams) {
                    thing {
                        physics {
                            bodyType = BodyDef.BodyType.StaticBody
                            fixture {
                                shape = rectangle
                            }
                        }
                    }
                    node(nestedParams) {
                        thing {
                            physics {
                                bodyType = BodyDef.BodyType.StaticBody
                                fixture {
                                    shape = rectangle
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun nestedPolygons(): SceneSpec {
        // The scale, rotation and translation of nested nodes happens in parents space.
        val nestedParams = params { x = 4.1f; y = 4.1f; scaleX = 0.5f; scaleY = 0.75f; rotationD = 45f }
        val polygonTriangle = Polygon(
                vec2(0f, 8f),
                vec2(-4f, 0f),
                vec2(4f, 0f)
        )
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                    fixture {
                        shape = polygonTriangle
                    }
                }
            }
            node(nestedParams) {
                thing {
                    physics {
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            shape = polygonTriangle
                        }
                    }
                }
                node(nestedParams) {
                    thing {
                        physics {
                            bodyType = BodyDef.BodyType.StaticBody
                            fixture {
                                shape = polygonTriangle
                            }
                        }
                    }
                    node(nestedParams) {
                        thing {
                            physics {
                                bodyType = BodyDef.BodyType.StaticBody
                                fixture {
                                    shape = polygonTriangle
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun nestedImages(): SceneSpec {
        val nestedParams = params { x = 4.1f; y = 4.1f; scaleX = 0.5f; scaleY = 0.75f; rotationD = 45f }
        return scene {
            thing {
                render {
                    imageTexture("textures/binarymonk.png") { width = 4f; height = 4f }
                }
            }
            node(nestedParams) {
                thing {
                    render {
                        imageTexture("textures/binarymonk.png") { width = 4f; height = 4f }
                    }
                }
                node(nestedParams) {
                    thing {
                        render {
                            imageTexture("textures/binarymonk.png") { width = 4f; height = 4f }
                        }
                    }
                    node(nestedParams) {
                        thing {
                            render {
                                imageTexture("textures/binarymonk.png") { width = 4f; height = 4f }
                            }
                        }
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