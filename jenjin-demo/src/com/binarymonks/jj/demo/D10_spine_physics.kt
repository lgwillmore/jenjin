package com.binarymonks.jj.demo

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.specs.Circle
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.physics.CollisionGroupSpecName
import com.binarymonks.jj.core.spine.specs.SpineSkeletonSpec
import com.binarymonks.jj.core.spine.specs.SpineSpec


class D10_spine_physics : Game(MyConfig10.jjConfig) {

    override fun gameOn() {
        JJ.physics.collisionGroups.buildGroups {
            group("character").collidesWith("hammer", "terrain")
            group("hammer").collidesWith("character")
            group("terrain").collidesWith("character")
        }

        JJ.scenes.addSceneSpec("spineBoy", spineBoy())
        JJ.scenes.addSceneSpec("hammer", swingHammer())
        JJ.scenes.addSceneSpec("terrain", terrain())
        JJ.scenes.loadAssetsNow()

        JJ.scenes.instantiate(scene {
            nodeRef(params { x = 5f; y = -0.25f }) { "spineBoy" }
            nodeRef(params { x = 8f; y = 4f; rotationD = 90f }) { "hammer" }
            nodeRef(params { y = -2f; scaleX = 15f }) { "terrain" }
            nodeRef(params { x = -7f; y = 1f; scaleY = 6f }) { "terrain" }
        })
    }

    fun spineBoy(): SceneSpecRef {
        return SpineSpec {
            atlasPath = "spine/spineboy/spineboy-pma.atlas"
            dataPath = "spine/spineboy/spineboy.json"
            startingAnimation = "walk"
            scale = 1f / 247f
            originY = 247f
            spineSkeleton = SpineSkeletonSpec {
                collisionGroup = CollisionGroupSpecName("character")
                overrideFixtureFor("head") {
                    shape = Circle(0.4f)
                    offsetX = 0.4f
                    collsionGroup("character")
                }
            }
        }
    }

    private fun swingHammer(): SceneSpec {
        return scene {
            node(params { name = "hammerAnchor" }) {
                thing { physics { bodyType = BodyDef.BodyType.StaticBody } }
            }
            node(params { name = "hammer" }) {
                thing {
                    physics {
                        fixture { shape = Rectangle(0.5f, 3f); offsetY = -1.5f; collsionGroup("hammer") }
                        fixture { shape = Rectangle(2f, 2f); offsetY = -4f; collsionGroup("hammer") }
                        fixture { shape = Circle(1f); offsetY = -4f; offsetX = -1f; collsionGroup("hammer") }
                        fixture { shape = Circle(1f); offsetY = -4f; offsetX = 1f; collsionGroup("hammer") }
                    }
                    render {
                        rectangleRender(0.5f, 3f) { color.set(Color.BROWN); offsetY = -1.5f }
                        rectangleRender(2f, 2f) { color.set(Color.GRAY); offsetY = -4f }
                        circleRender(1f) { color.set(Color.GRAY); offsetY = -4f; offsetX = -1f }
                        circleRender(1f) { color.set(Color.GRAY); offsetY = -4f; offsetX = 1f }
                        circleRender(0.25f) { color.set(Color.GREEN); }
                    }
                }
            }
            revJoint("hammer", "hammerAnchor", vec2()) {}
        }
    }

    fun terrain(): SceneSpec {
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.StaticBody
                    fixture {
                        shape = Rectangle(1f, 1f)
                        collsionGroup("terrain")
                    }
                }
                render {
                    rectangleRender(1f, 1f) { color.set(Color.GREEN) }
                }
            }
        }
    }

}

object MyConfig10 {
    var jjConfig: JJConfig = JJConfig()

    init {
//        jjConfig.b2d.debug = true

        jjConfig.gameView.worldBoxWidth = 15f
        jjConfig.gameView.cameraPosX = 0f
        jjConfig.gameView.cameraPosY = 0f
    }
}


