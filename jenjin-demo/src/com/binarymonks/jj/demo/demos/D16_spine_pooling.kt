package com.binarymonks.jj.demo.demos

import com.badlogic.gdx.graphics.Color
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.JJGame
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.components.misc.Emitter
import com.binarymonks.jj.core.components.misc.SelfDestruct
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.core.specs.builders.component
import com.binarymonks.jj.core.specs.builders.nodeRef
import com.binarymonks.jj.core.specs.builders.params
import com.binarymonks.jj.core.specs.builders.scene
import com.binarymonks.jj.core.spine.specs.SpineSpec


class D16_spine_pooling : JJGame(JJConfig {
    b2d.debug = true
    val width = 6f
    gameView.worldBoxWidth = width
    gameView.cameraPosX = 0f
    gameView.cameraPosY = 2f
    gameView.clearColor = Color(0.2f, 0.2f, 0.2f, 1f)
}) {

    override fun gameOn() {
        JJ.scenes.addSceneSpec("spineDummy", spine_with_bounding_boxes())
        JJ.scenes.addSceneSpec("spawn", spawner())
        JJ.scenes.loadAssetsNow()

        JJ.scenes.instantiate(scene {
            nodeRef(params { x=-2f }) { "spawn" }
            nodeRef(params { x=2f }) { "spawn" }
        })

    }

    fun spawner(): SceneSpecRef{
        return scene {
            component(Emitter()){
                setSpec("spineDummy")
                delayMinSeconds=3f
                delayMaxSeconds=5f
                repeat=0
            }
        }
    }

    fun spine_with_bounding_boxes(): SceneSpecRef {
        return SpineSpec {
            spineRender {
                atlasPath = "spine/male_base.atlas"
                dataPath = "spine/male_base.json"
                scale = 1.5f / 103f
            }
            skeleton {
                boundingBoxes = true
            }
            rootScene {
                component(SelfDestruct()){
                    delaySeconds=2f
                }
            }
        }
    }
}