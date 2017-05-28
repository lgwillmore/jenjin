package com.binarymonks.jj.demo

import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.physics.collisions.SoundCollision
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.physics.Circle

/**
 * Collisions are obviously very important.
 *
 * To hook into the collision lifecycle you need to implement a [com.binarymonks.jj.core.physics.collisions.CollisionHandler]
 *
 * Here we use a built in [com.binarymonks.jj.core.physics.collisions.CollisionHandler] to trigger a sound.
 *
 * Deciding what can collide with what is also important for how your physics behaves with things such as layers, projectiles and terrain.
 *
 * For that you can configure your collision groups with [com.binarymonks.jj.core.JJ.physics]
 * and then use [com.binarymonks.jj.core.specs.physics.FixtureSpec.collisionGroup] or
 * [com.binarymonks.jj.core.specs.physics.FixtureSpec.collisionGroupProperty].
 *
 * Or you can do it all yourself and set your fixture collision data explicitly with [com.binarymonks.jj.core.specs.physics.FixtureSpec.collisionGroupExplicit]
 */
class D03_collisions : Game(MyConfig03.jjConfig) {

    public override fun gameOn() {

        JJ.scenes.addSceneSpec("ball", ball())
        JJ.scenes.addSceneSpec("floor", floor())

        JJ.scenes.loadAssetsNow()

        //Set up our collision groups
        JJ.physics.collisionGroups.buildGroups {
            group("layer1").collidesWith("layer1")
            group("layer2").collidesWith("layer2")
        }

        val initialSceneSpec = scene {
            //Set up some balls and a floor on 'layer1' collision group
            nodeRef(params { x = -8f; y = 8f; setProperty("collisionGroup", "layer1") }) { "ball" }
            nodeRef(params { x = -2f; y = 9f; scaleX = 2f; scaleY = 2f;setProperty("collisionGroup", "layer1") }) { "ball" }
            nodeRef(params { x = 0f; y = 0f; scaleX = 20f;setProperty("collisionGroup", "layer1") }) { "floor" }
            //Set up some balls and a floor on 'layer2' collision group
            nodeRef(params { x = 2f; y = 10f;setProperty("collisionGroup", "layer2") }) { "ball" }
            nodeRef(params { x = +8f; y = 11f; scaleX = 2f; scaleY = 2f;setProperty("collisionGroup", "layer2") }) { "ball" }
            nodeRef(params { x = 0f; y = -10f; scaleX = 20f;setProperty("collisionGroup", "layer2") }) { "floor" }
        }

        JJ.scenes.instantiate(initialSceneSpec)
    }

    private fun ball(): SceneSpec {
        return scene {
            thing {
                sound("bounce", "sounds/pong.mp3") {
                    volume = 0.6f
                }
                physics {
                    fixture {
                        shape = Circle()
                        restitution = 0.7f
                        // We bind the collision group to a property key
                        collisionGroupProperty("collisionGroup")
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
                        // We bind the collision group to a property key
                        collisionGroupProperty("collisionGroup")
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
        jjConfig.gameViewConfig.cameraPosY = 0f
    }
}
