package com.binarymonks.jj.demo.demos

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.JJGame
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*
import com.bitfire.postprocessing.effects.Pixels


class D13_postprocessing : JJGame(JJConfig {
    gameView.clearColor = Color(0.5f, 0.7f, 1f, 1f)
    gameView.worldBoxWidth = 6f
    gameView.cameraPosX = 0f
    gameView.cameraPosY = 0f
    gameView.postProcessingEnabled=true
}) {
    override fun gameOn() {

        JJ.scenes.addSceneSpec("image", image())
        JJ.scenes.addSceneSpec("shape", shape())
        JJ.scenes.loadAssetsNow()

        JJ.B.renderWorld.postProcessor.addEffect(Pixels(0.005f, 0.005f))
        JJ.B.renderWorld.postProcessor.setClearColor(Color(0.5f, 0.7f, 1f, 1f))

        JJ.scenes.instantiate(scene {
            nodeRef { "image" }
            nodeRef { "shape" }
        })

    }
}

fun image(): SceneSpec {
    return scene {
        render {
            imageTexture("textures/landscape.png") {
                width = 6f
                height = 4.5f
            }
        }
    }
}

fun shape(): SceneSpec {
    return scene {
        render {
            rectangleRender(1f, 1f) {
                color.set(Color.BLUE)
                rotationD = 45f
            }
        }
    }
}