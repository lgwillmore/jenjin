package com.binarymonks.jj.demo

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.Circle
import com.binarymonks.jj.core.specs.Polygon
import com.binarymonks.jj.core.specs.Rectangle

/**
 * A SceneNode's transforms (translation, rotation, scale) operate in the space of the parent.
 * All the way up to the global b2d world space.
 */
class D01_nested_transforms : Game(MyConfig01.jjConfig) {

    public override fun gameOn() {
        JJ.scenes.addSceneSpec("nestedCircles", nestedCircles())
        JJ.scenes.addSceneSpec("nestedRectangles", nestedRectangles())
        JJ.scenes.addSceneSpec("nestedPolygons", nestedPolygons())
        JJ.scenes.loadAssetsNow()

        val initialSceneSpec = scene {
            nodeRef(params { x = 10f;y = 6f }) { "nestedCircles" }
            nodeRef(params { x = 20f;y = 6f; scaleX = 0.5f; scaleY = 0.5f }) { "nestedCircles" }
            nodeRef(params { x = -8f; y = 6f }) { "nestedRectangles" }
            nodeRef(params { x = -18f; y = 6f; scaleX = 0.5f; scaleY = 0.5f }) { "nestedRectangles" }
            nodeRef(params { x = 10f; y = -15f }) { "nestedPolygons" }
            nodeRef(params { x = 20f; y = -15f; scaleX = 0.5f; scaleY = 0.5f }) { "nestedPolygons" }
        }

        JJ.scenes.instantiate(initialSceneSpec)
    }

    private fun nestedCircles(): SceneSpec {
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
                render {
                    circleRender(4f) {
                        color.setToValue(Color.PURPLE)
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
                    render {
                        circleRender(4f) {
                            color.setToValue(Color.PURPLE)
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
                        render {
                            circleRender(4f) {
                                color.setToValue(Color.PURPLE)
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
                            render {
                                circleRender(4f) {
                                    color.setToValue(Color.PURPLE)
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
                    render {
                        circleRender(4f) {
                            color.setToValue(Color.PURPLE)
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
                        render {
                            circleRender(4f) {
                                color.setToValue(Color.PURPLE)
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
                            render {
                                circleRender(4f) {
                                    color.setToValue(Color.PURPLE)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun nestedRectangles(): SceneSpec {
        val nestedParams = params { x = 4.1f; y = 4.1f; scaleX = 0.5f; scaleY = 0.75f; rotationD = 45f }
        val imageWidth = 8f
        val imageheight = 8f
        val rectangle = Rectangle(imageWidth, imageheight)
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                    fixture {
                        shape = rectangle
                    }
                    render {
                        imageTexture("textures/binarymonk.png") { width = imageWidth; height = imageheight }
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
                        render {
                            imageTexture("textures/binarymonk.png") { width = imageWidth; height = imageheight }
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
                            render {
                                imageTexture("textures/binarymonk.png") { width = imageWidth; height = imageheight }
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
                                render {
                                    imageTexture("textures/binarymonk.png") { width = imageWidth; height = imageheight }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun nestedPolygons(): SceneSpec {
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
                render {
                    polygonRender(polygonTriangle.vertices) {
                        color.setToValue(Color.CYAN)
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
                    render {
                        polygonRender(polygonTriangle.vertices) {
                            color.setToValue(Color.CYAN)
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
                        render {
                            polygonRender(polygonTriangle.vertices) {
                                color.setToValue(Color.CYAN)
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
                            render {
                                polygonRender(polygonTriangle.vertices) {
                                    color.setToValue(Color.CYAN)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

object MyConfig01 {
    var jjConfig: JJConfig = JJConfig()

    init {
        jjConfig.b2dConfig.debug = true

        jjConfig.gameViewConfig.worldBoxWidth = 50f
        jjConfig.gameViewConfig.cameraPosX = 0f
        jjConfig.gameViewConfig.cameraPosY = 0f
    }
}