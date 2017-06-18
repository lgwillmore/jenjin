package com.binarymonks.jj.demo

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.render.LightGraph

class D06_lights : Game(MyConfig06.jjConfig) {

    override fun gameOn() {

        JJ.assets.loadNow("textures/circuit_background.png", Texture::class)

        JJ.render.setAmbientLight(0f, 0f, 0f, 0.4f)

        JJ.scenes.instantiate(scene {
            node {
                render { imageTexture("textures/circuit_background.png") { width = 20f; height = 40f } }
            }
            node(params { x = -5f; y = 5f }) {
                physics { fixture { shape = Rectangle(2.5f, 2.5f) } }
                render { rectangleRender(2.5f, 2.5f) { color.set(Color.BLUE) } }
            }
            node(params { x = 5f; y = -5f }) {
                physics { fixture { shape = Rectangle(2.5f, 2.5f) } }
                render { rectangleRender(2.5f, 2.5f) { color.set(Color.BLUE) } }
            }
            node {
                physics {
                    fixture { shape = Rectangle(1.5f, 1.5f) }
                    pointLight {
                        reach = 20f
                        color.set(Color.LIME)
                    }
                }
                render {
                    rectangleRender(1.5f, 1.5f) {
                        color.set(Color.WHITE)
                        graphID = LightGraph()
                        color.set(Color.LIME)
                    }
                }
            }
        })
    }
}

object MyConfig06 {
    var jjConfig: JJConfig = JJConfig()

    init {
        jjConfig.b2d.debug = false
        jjConfig.b2d.gravity = Vector2()

        jjConfig.gameView.worldBoxWidth = 20f
        jjConfig.gameView.cameraPosX = 0f
        jjConfig.gameView.cameraPosY = 0f
    }
}