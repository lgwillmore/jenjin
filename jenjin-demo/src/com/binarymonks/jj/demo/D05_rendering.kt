package com.binarymonks.jj.demo

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*


class D05_rendering : Game() {
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
            thing {
                physics{
                    bodyType=BodyDef.BodyType.StaticBody
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