package com.binarymonks.jj.demo

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.specs.Chain
import com.binarymonks.jj.core.specs.Circle
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*


class D07_b2d_composite : Game(MyConfig07.jjConfig) {


    override fun gameOn() {

        JJ.scenes.addSceneSpec("swingHammer", swingHammer())
        JJ.scenes.addSceneSpec("spinner", spinner())
        JJ.scenes.addSceneSpec("terrain", floor())

        JJ.scenes.instantiate(scene {
            nodeRef(params { x = 8f; y = 7f; rotationD = -90f;scaleX = 0.5f; scaleY = 0.5f }) { "swingHammer" }
            nodeRef(params { x = 8f; y = 4f; scaleX = 0.5f; scaleY = 0.5f }) { "spinner" }
            nodeRef { "terrain" }
            node(params { x = 7.5f; y = 2.5f }) {
                thing {
                    physics {
                        fixture { shape = Circle(0.5f) }
                    }
                    render { circleRender(0.5f) { color.set(Color.PURPLE) } }
                }
            }
        })

    }

    private fun floor(): SceneSpec {
        val floor = Chain(
                vec2(-10f, 2f),
                vec2(-8f, 1f),
                vec2(-6f, 0f),
                vec2(-4f, 0f),
                vec2(-2f, 0f),
                vec2(0f, 1f),
                vec2(2f, 1f),
                vec2(4f, 1f),
                vec2(6f, 2f),
                vec2(8f, 2f),
                vec2(10f, 2f)
        )
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                    fixture {
                        shape = floor
                    }
                }
                render {
                    lineChainRender(floor.vertices) {
                        color.set(Color.GREEN)
                    }
                }
            }
        }
    }

    private fun spinner(): SceneSpec {
        //Some forced weld joints, but they work
        return scene {
            node(params { name = "anchor" }) {
                thing {
                    physics { bodyType = BodyDef.BodyType.StaticBody }
                    render { circleRender(0.25f) { color.set(Color.GREEN); layer = 1 } }
                }
            }
            node(params { name = "arm" }) {
                thing {
                    physics { fixture { shape = Rectangle(0.5f, 4f) } }
                    render { rectangleRender(0.5f, 4f) { color.set(Color.BROWN) } }
                }
            }
            node(params { name = "topBall"; y = 2f }) {
                thing {
                    physics {
                        fixture { shape = Circle(1f) }
                    }
                    render {
                        circleRender(1f) { color.set(Color.GRAY) }
                    }
                }
            }
            node(params { name = "bottomBall"; y = -2f }) {
                thing {
                    physics {
                        fixture { shape = Circle(1f) }
                    }
                    render {
                        circleRender(1f) { color.set(Color.GRAY) }
                    }
                }
            }
            revJoint("anchor", "arm", vec2()) {}
            weldJoint("arm", "topBall", vec2(0f, 2f)) {}
            weldJoint("arm", "bottomBall", vec2(0f, -2f)) {}
        }
    }

    private fun swingHammer(): SceneSpec {
        return scene {
            node(params { name = "hammerAnchor" }) {
                thing { physics { bodyType = BodyDef.BodyType.StaticBody } }
            }
            node(params { name = "hammer" }) {
                thing {
                    physics {
                        fixture { shape = Rectangle(0.5f, 3f); offsetY = -1.5f }
                        fixture { shape = Rectangle(2f, 2f); offsetY = -4f }
                        fixture { shape = Circle(1f); offsetY = -4f; offsetX = -1f }
                        fixture { shape = Circle(1f); offsetY = -4f; offsetX = 1f }
                    }
                    render {
                        rectangleRender(0.5f, 3f) { color.set(Color.BROWN); offsetY = -1.5f }
                        rectangleRender(2f, 2f) { color.set(Color.GRAY); offsetY = -4f }
                        circleRender(1f) { color.set(Color.GRAY); offsetY = -4f; offsetX = -1f }
                        circleRender(1f) { color.set(Color.GRAY); offsetY = -4f; offsetX = 1f }
                        circleRender(0.25f) { color.set(Color.GREEN); }
                    }
                }
            }
            revJoint("hammer", "hammerAnchor", vec2()) {}
        }
    }
}

object MyConfig07 {
    var jjConfig: JJConfig = JJConfig()

    init {
        jjConfig.b2d.debug = false
//        jjConfig.b2d.gravity = Vector2()

        jjConfig.gameView.worldBoxWidth = 20f
        jjConfig.gameView.cameraPosX = 0f
        jjConfig.gameView.cameraPosY = 0f
    }
}