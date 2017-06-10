package com.binarymonks.jj.demo

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.specs.Chain
import com.binarymonks.jj.core.specs.Circle
import com.binarymonks.jj.core.specs.Polygon
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*


class D05_rendering : Game(MyConfig05.jjConfig) {
    override fun gameOn() {
        JJ.scenes.addSceneSpec("shapes", shapes())

        val initialScene = scene {
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
                        color.set(Color.GREEN)
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
        val zigzag = Chain(
                vec2(-2f, 0f),
                vec2(-1f, 1f),
                vec2(0f, 0f),
                vec2(1f, 1f),
                vec2(2f, 0f)
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
                            color.set(Color.RED)
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
                            color.set(Color.GREEN)
                            offsetX = -4f
                        }
                    }
                }
            }
            //LineChain
            node {
                thing {
                    physics {
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            shape = zigzag
                            offsetY = -4f
                            rotationD = 90f
                        }
                    }
                    render {
                        lineChainRender(zigzag.vertices) {
                            color.set(Color.BLUE)
                            offsetY = -4f
                            rotationD = 90f
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
        jjConfig.b2d.debug = true

        jjConfig.gameView.worldBoxWidth = 30f
        jjConfig.gameView.cameraPosX = 0f
        jjConfig.gameView.cameraPosY = 0f
    }
}