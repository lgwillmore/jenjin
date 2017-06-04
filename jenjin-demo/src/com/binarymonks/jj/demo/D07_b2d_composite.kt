package com.binarymonks.jj.demo

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.specs.Circle
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*


class D07_b2d_composite : Game(MyConfig07.jjConfig) {


    override fun gameOn() {

        JJ.scenes.addSceneSpec("swingHammer", swingHammer())

        JJ.scenes.instantiate(scene {
            nodeRef(params { x = 0f; y = 7f; rotationD = -90f;scaleX = 0.5f; scaleY = 0.5f }) { "swingHammer" }
        })

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
        jjConfig.b2dConfig.debug = false
//        jjConfig.b2dConfig.gravity = Vector2()

        jjConfig.gameViewConfig.worldBoxWidth = 20f
        jjConfig.gameViewConfig.cameraPosX = 0f
        jjConfig.gameViewConfig.cameraPosY = 0f
    }
}