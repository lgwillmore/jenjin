package com.binarymonks.jj.demo

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*

/**
 * [com.binarymonks.jj.core.specs.render.RenderNodeSpec.layer] Controls the layers that a Render node appears in relation to the layers
 * of other [com.binarymonks.jj.core.things.Thing] layers.
 *
 * [com.binarymonks.jj.core.specs.render.RenderNodeSpec.priority] Controls the render order
 * of the [com.binarymonks.jj.core.things.Thing] nodes as they appear in a Layer.
 *
 * This lets you add arbitrary internal layers to a [com.binarymonks.jj.core.things.Thing] to build the composite render you want. At the same clock
 * it lets [com.binarymonks.jj.core.things.Thing]s in the world with interleaving layers render as you would expect them to when they overlap.
 *
 * If 1 of your objects suddenly needs more layer complexity
 * - this does not spill out into having to make your world have more layer complexity and having to shuffle everything around.
 *
 * In this example - there are only 3 world layers, but there are also several internal object layers.
 */
class D02_rendering_layers : Game(MyConfig02.jjConfig) {

    public override fun gameOn() {

        JJ.scenes.addSceneSpec("0and2", layer0And2())
        JJ.scenes.addSceneSpec("1", layer1())
        JJ.scenes.loadAssetsNow()

        val initialSceneSpec = scene {
            nodeRef { "0and2" }
            nodeRef(params { y = 10f }) { "1" }
        }

        JJ.scenes.instantiate(initialSceneSpec)
    }

    private fun layer0And2(): SceneSpec {
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                }
                render {
                    imageTexture("textures/layers/0_0.png") {
                        color.set(Color.CORAL)
                        layer = 0
                        priority = 0
                        width = 35f
                        height = 35f
                    }
                    imageTexture("textures/layers/0_1.png") {
                        color.set(Color.ORANGE)
                        layer = 0
                        priority = 1
                        offsetX = 2.5f
                        offsetY = 2.5f
                        width = 30f
                        height = 30f
                    }
                    imageTexture("textures/layers/2_0.png") {
                        color.set(Color.BLUE)
                        layer = 2
                        priority = 0
                        offsetX = 10f
                        offsetY = 10f
                        width = 15f
                        height = 15f
                    }
                    imageTexture("textures/layers/2_1.png") {
                        color.set(Color.CYAN)
                        layer = 2
                        priority = 1
                        offsetX = 12.5f
                        offsetY = 12.5f
                        width = 10f
                        height = 10f
                    }
                }
            }
        }
    }

    private fun layer1(): SceneSpec {
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                }
                render {
                    imageTexture("textures/layers/1_0.png") {
                        color.set(Color.GREEN)
                        layer = 1
                        priority = 0
                        width = 25f
                        height = 25f
                    }
                    imageTexture("textures/layers/1_1.png") {
                        color.set(Color.YELLOW)
                        layer = 1
                        priority = 1
                        offsetX = 5f
                        offsetY = 5f
                        width = 15f
                        height = 15f
                    }
                }
            }
        }
    }
}


object MyConfig02 {
    var jjConfig: JJConfig = JJConfig()

    init {
        jjConfig.b2dConfig.debug = true

        jjConfig.gameViewConfig.worldBoxWidth = 50f
        jjConfig.gameViewConfig.cameraPosX = 0f
        jjConfig.gameViewConfig.cameraPosY = 0f
    }
}