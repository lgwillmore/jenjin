package com.binarymonks.jj.demo

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.components.Emitter
import com.binarymonks.jj.core.components.SelfDestruct
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.specs.Circle
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.sceneRef


val spawnDelay = 0.001f
val destroyDelay = 1f

class D08_pooling_and_destroying : Game(MyConfig08.jjConfig) {
    override fun gameOn() {

        JJ.scenes.addSceneSpec("nestedCompositeScene", nestedCompositeScene())
        JJ.scenes.addSceneSpec("circle", circle())


        JJ.scenes.instantiate(scene {
            //generator
            node(params { y = -3f;x = 3f }) {
                thing {
                    component(Emitter()) { sceneSpecRef = sceneRef("nestedCompositeScene"); delaySeconds = spawnDelay }
                    render {
                        circleRender(0.5f) { color.set(Color.YELLOW) }
                    }
                }
            }
            //scaled generator
            node(params { y = 3f;x = -3f }) {
                thing {
                    component(Emitter()) { sceneSpecRef = sceneRef("nestedCompositeScene"); delaySeconds = spawnDelay; scaleX = 0.5f; scaleY = 0.5f }
                    render {
                        circleRender(0.5f) { color.set(Color.PURPLE) }
                    }
                }
            }
        })

    }

    private fun circle(): SceneSpec {
        return scene {
            thing {
                physics {
                    fixture { shape = Circle(1f) }
                }
                render {
                    circleRender(1f) { color.set(Color.GREEN) }
                }
            }
        }
    }

    private fun nestedCompositeScene(): SceneSpec {
        return scene {
            thing {
                component(SelfDestruct()) {
                    delaySeconds = destroyDelay
                }
            }
            node(params { name = "rectangle" }) {
                thing {
                    physics {
                        fixture { shape = Rectangle(1f, 1f) }
                    }
                    render {
                        rectangleRender(1f, 1f) { color.set(Color.BLUE) }
                    }
                }
            }
            nodeRef(params { name = "circle"; y = 1f }) { "circle" }
            weldJoint("rectangle", "circle", vec2()) {}
        }
    }
}

object MyConfig08 {
    var jjConfig: JJConfig = JJConfig()

    init {
        jjConfig.b2dConfig.debug = false
        jjConfig.b2dConfig.gravity = Vector2()

        jjConfig.gameViewConfig.worldBoxWidth = 20f
        jjConfig.gameViewConfig.cameraPosX = 0f
        jjConfig.gameViewConfig.cameraPosY = 0f
    }
}