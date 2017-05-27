package com.binarymonks.jj.demo

import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.physics.collisions.SoundCollision
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.physics.Circle

class D03_collisions: Game(MyConfig01.jjConfig) {

    public override fun gameOn() {

        JJ.scenes.addSceneSpec("ball", ball())
        JJ.scenes.addSceneSpec("floor", floor())

        JJ.scenes.loadAssetsNow()

        val initialSceneSpec = scene {
            nodeRef(params { x = -8f; y = 8f }) { "ball" }
            nodeRef(params { x = -2f; y = 9f; scaleX = 2f; scaleY = 2f }) { "ball" }
            nodeRef(params { x = 2f; y = 10f }) { "ball" }
            nodeRef(params { x = +8f; y = 11f }) { "ball" }
            nodeRef(params { x = 0f; y = 0f; scaleX = 20f }) { "floor" }
        }

        JJ.scenes.instantiate(initialSceneSpec)
    }

    private fun ball(): SceneSpec {
        return scene {
            thing {
                sound("bounce", "sounds/pong.mp3"){
                    volume = 0.6f
                }
                physics {
                    fixture {
                        shape = Circle()
                        restitution = 0.7f
                    }
                    beginCollision(SoundCollision(soundName = "bounce"))
                }
            }
        }
    }

    private fun floor(): SceneSpec {
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                    fixture {
                    }
                }
            }
        }
    }
}



object MyConfig03 {
    var jjConfig: JJConfig = JJConfig()
    init {
        jjConfig.b2dConfig.debug = true

        jjConfig.gameViewConfig.worldBoxWidth = 30f
        jjConfig.gameViewConfig.cameraPosX = 0f
        jjConfig.gameViewConfig.cameraPosY = 10f
    }
}
