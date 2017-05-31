package com.binarymonks.jj.demo

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.specs.Circle
import com.binarymonks.jj.core.specs.Polygon
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*


val poly = Polygon(
        vec2(-2f, 0f),
        vec2(-1.5f, 1.5f),
        vec2(0f, 2f),
        vec2(1.5f, 1.5f),
        vec2(2f, 0f),
        vec2(1.5f, -1.5f),
        vec2(0f, -2f),
        vec2(-1.5f, -1.5f)
)
val poly2 = Polygon(
        vec2(-4f, 0f),
        vec2(-3f, 3f),
        vec2(0f, 4f),
        vec2(3f, 3f),
        vec2(4f, 0f),
        vec2(3f, -3f),
        vec2(0f, -4f),
        vec2(-3f, -3f)
)


class D05_rendering : Game(MyConfig05.jjConfig) {
    override fun gameOn() {
        JJ.scenes.addSceneSpec("shapes", shapes())

        val initialScene = scene {
            nodeRef(params { y = -5f; scaleX = 0.5f; scaleY = 0.5f }) { "shapes" }
            nodeRef { "shapes" }
        }

        JJ.scenes.instantiate(initialScene)
    }

    private fun line(): SceneSpec {
        return scene {
            thing {
                render {
                    polygonRender(
                            vec2(0f, 0f),
                            vec2(0f, 2f),
                            vec2(0f, 2f),
                            vec2(0f, 0f)
                    ) {
                        color.setToValue(Color.GREEN)
                    }
                }
            }
        }
    }

    //We can draw shapes - with the same dimensions as our fixtures
    private fun shapes(): SceneSpec {
        val poly = Polygon(
                vec2(-2f, 0f),
                vec2(-1.5f, 1.5f),
                vec2(0f, 2f),
                vec2(1.5f, 1.5f),
                vec2(2f, 0f),
                vec2(1.5f, -1.5f),
                vec2(0f, -2f),
                vec2(-1.5f, -1.5f)
        )
        return scene {
            // Polygon
            node {
                thing {
                    physics {
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            offsetX = 4f
                            shape = poly
                        }
                    }
                    render {
                        polygonRender(poly.vertices) {
                            offsetX = 4f
                            color.setToValue(Color.RED)
                        }
                    }
                }
                node(params { x = 4f; scaleX = 0.7f; scaleY = 0.7f }) {
                    thing {
                        physics {
                            bodyType = BodyDef.BodyType.StaticBody
                            fixture {
                                offsetX = 4f
                                shape = poly
                            }
                        }
                        render {
                            polygonRender(poly.vertices) {
                                offsetX = 4f
                                color.setToValue(Color.RED)
                            }
                        }
                    }
                }
            }
            // Circle
            node {
                thing {
                    physics {
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            offsetX = -4f
                            shape = Circle(2f)
                        }
                    }
                    render {
                        circleRender(2f) {
                            color.setToValue(Color.GREEN)
                            offsetX = -4f
                        }
                    }
                }
                node(params { x = -4f; scaleX = 0.7f; scaleY = 0.7f }) {
                    thing {
                        physics {
                            bodyType = BodyDef.BodyType.StaticBody
                            fixture {
                                offsetX = -4f
                                shape = Circle(2f)
                            }
                        }
                        render {
                            circleRender(2f) {
                                color.setToValue(Color.GREEN)
                                offsetX = -4f
                            }
                        }
                    }
                }
            }
        }
    }
}

object MyConfig05 {
    var jjConfig: JJConfig = JJConfig()

    init {
        jjConfig.b2dConfig.debug = true

        jjConfig.gameViewConfig.worldBoxWidth = 30f
        jjConfig.gameViewConfig.cameraPosX = 0f
        jjConfig.gameViewConfig.cameraPosY = 0f
    }
}