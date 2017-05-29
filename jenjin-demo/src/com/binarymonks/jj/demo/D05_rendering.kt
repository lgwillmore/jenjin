package com.binarymonks.jj.demo

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
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

    //We can draw shapes
    private fun shapes(): SceneSpec {
        return scene {
            // Rectangle
            node{
                thing {
                    physics{
                        bodyType=BodyDef.BodyType.StaticBody
                        fixture{
                            shape=Rectangle(2f,2f)
                        }
                    }
                    render {
                        shape {
                            color.setToValue(Color.BLUE)
                            shape=Rectangle(2f,2f)
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