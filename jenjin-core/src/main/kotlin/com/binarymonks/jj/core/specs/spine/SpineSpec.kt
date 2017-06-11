package com.binarymonks.jj.core.specs.spine

import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.components.spine.SpineComponent
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.*


class SpineSpec() {

    var atlasPath: String? = null
    var dataPath: String? = null
    var scale: Float = 1f
    var originX: Float = 0f
    var originY: Float = 0f
    var startingAnimation: String? = null
    var spineSkeleton: SpineSkeletonSpec? = null


    constructor(build: SpineSpec.() -> Unit) : this() {
        this.build()
    }


    fun toSceneSpec(): SceneSpec {
        return scene {
            thing {
                physics {
                    bodyType = BodyDef.BodyType.KinematicBody
                }
                render {
                    renderNodes.add(
                            SpineRenderNodeSpec(
                                    atlasPath,
                                    dataPath,
                                    originX,
                                    originY,
                                    scale
                            )
                    )
                }
                component(SpineComponent()) {
                    startingAnimation = this@SpineSpec.startingAnimation
                }
            }
        }
    }
}

