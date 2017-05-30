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


class D05_rendering : Game(MyConfig05.jjConfig) {
    override fun gameOn() {
        JJ.scenes.addSceneSpec("shapes", shapes())

        val initialScene = scene {
            nodeRef { "shapes" }
        }

        JJ.scenes.instantiate(initialScene)
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
            // Rectangle
            node {
                thing {
                    physics {
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            offsetY = 3f
                            shape = Rectangle(2f, 2f)
                        }
                    }
                    render {
                        shape {
                            offsetY = 3f
                            color.setToValue(Color.BLUE)
                            shape = Rectangle(2f, 2f)
                        }
                    }
                }
                node(params { y = 3f ; scaleX=0.7f; scaleY=0.7f}) {
                    thing {
                        physics {
                            bodyType = BodyDef.BodyType.StaticBody
                            fixture {
                                offsetY = 3f
                                shape = Rectangle(2f, 2f)
                            }
                        }
                        render {
                            shape {
                                offsetY = 3f
                                color.setToValue(Color.BLUE)
                                shape = Rectangle(2f, 2f)
                            }
                        }
                    }
                }
            }
            // Polygon
            node {
                thing {
                    physics {
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            offsetX = 3f
                            shape = poly
                        }
                    }
                    render {
                        shape {
                            offsetX = 3f
                            color.setToValue(Color.RED)
                            shape = poly
                        }
                    }
                }
                node(params { x = 3f; scaleX = 0.7f; scaleY = 0.7f }) {
                    thing {
                        physics {
                            bodyType = BodyDef.BodyType.StaticBody
                            fixture {
                                offsetX = 3f
                                shape = poly
                            }
                        }
                        render {
                            shape {
                                offsetX = 3f
                                color.setToValue(Color.RED)
                                shape = poly
                            }
                        }
                    }
                }
            }
            // Circle
            node{
                thing{
                    physics{
                        bodyType = BodyDef.BodyType.StaticBody
                        fixture {
                            offsetX=-3f
                            shape=Circle(1.5f)
                        }
                    }
                    render{
                        shape {
                            color.setToValue(Color.GREEN)
                            offsetX=-3f
                            shape=Circle(1.5f)
                        }
                    }
                }
                node(params { x = -3f; scaleX = 0.7f; scaleY = 0.7f }){
                    thing{
                        physics{
                            bodyType = BodyDef.BodyType.StaticBody
                            fixture {
                                offsetX=-3f
                                shape=Circle(1.5f)
                            }
                        }
                        render{
                            shape {
                                color.setToValue(Color.GREEN)
                                offsetX=-3f
                                shape=Circle(1.5f)
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