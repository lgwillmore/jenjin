package com.binarymonks.jj.demo.demos

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.JJGame
import com.binarymonks.jj.core.components.input.Draggable
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.render.RenderGraphType

/**
 * Be sure to enable touch in the JJConfig to use [com.binarymonks.jj.core.components.input.TouchHandler]s
 *
 * And also dim the Ambient light to be able to see your lights.
 */
class D06_lights_and_touch : JJGame(MyConfig06.jjConfig) {

    override fun gameOn() {

        JJ.scenes.addSceneSpec("box", box())
        JJ.assets.loadNow("textures/circuit_background.png", com.badlogic.gdx.graphics.Texture::class)

        JJ.render.setAmbientLight(0f, 0f, 0f, 0.4f)

        JJ.scenes.instantiate(scene {
            node {
                render { imageTexture("textures/circuit_background.png") { width = 20f; height = 40f} }
            }
            nodeRef(params { x = -5f; y = 5f }) { "box" }
            nodeRef(params { x = 5f; y = -5f }) { "box" }

            /**
             * A touch draggable light
             */
            node {
                physics {
                    bodyType = BodyDef.BodyType.KinematicBody
                    fixture { shape = Rectangle(1.5f, 1.5f) }
                    pointLight {
                        reach = 20f
                        color.set(Color.LIME)
                    }
                }
                render {
                    rectangleRender(1.5f, 1.5f) {
                        renderGraphType = RenderGraphType.LIGHT
                        color.set(Color.LIME)
                    }
                }
                component(Draggable())
            }
        })
    }

    private fun box(): SceneSpecRef {
        return scene {
            physics {
                bodyType = BodyDef.BodyType.DynamicBody
                linearDamping = 1f
                fixture { shape = Rectangle(2.5f, 2.5f) }
            }
            render { rectangleRender(2.5f, 2.5f) { color.set(Color.BLUE) } }
            component(Draggable())
        }
    }
}

object MyConfig06 {
    var jjConfig: JJConfig = JJConfig()

    init {
        MyConfig06.jjConfig.b2d.debug = false
        MyConfig06.jjConfig.b2d.gravity = com.badlogic.gdx.math.Vector2()

        MyConfig06.jjConfig.gameView.worldBoxWidth = 20f
        MyConfig06.jjConfig.gameView.cameraPosX = 0f
        MyConfig06.jjConfig.gameView.cameraPosY = 0f


    }
}