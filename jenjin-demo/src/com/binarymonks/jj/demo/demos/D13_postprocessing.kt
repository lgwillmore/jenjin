package com.binarymonks.jj.demo.demos

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.JJGame
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*
import com.bitfire.postprocessing.effects.Pixels
import com.bitfire.postprocessing.effects.Vignette


class D13_postprocessing : JJGame(JJConfig {
    gameView.worldBoxWidth = 6f
    gameView.cameraPosX = 0f
    gameView.cameraPosY = 0f
}) {
    override fun gameOn() {

        JJ.scenes.addSceneSpec("image", image())
        JJ.scenes.addSceneSpec("shape", shape())
        JJ.scenes.loadAssetsNow()

//        JJ.B.renderWorld.postProcessor.addEffect(Vignette(Gdx.graphics.width, Gdx.graphics.height, false))
        JJ.B.renderWorld.postProcessor.addEffect(Pixels(5f, 5f))

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
                rotationD=45f
            }
        }
    }
}