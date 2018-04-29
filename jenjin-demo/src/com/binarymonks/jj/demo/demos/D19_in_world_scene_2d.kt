package com.binarymonks.jj.demo.demos

import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.JJGame
import com.binarymonks.jj.core.specs.Circle
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.params


/**
 * Attach Scene2D Actors to a scene.
 *
 */
class D19_in_world_scene_2d : JJGame(JJConfig {
    b2d.debugRender = true
    gameView.worldBoxWidth = 30f
    gameView.cameraPosX = 0f
    gameView.cameraPosY = 10f
}) {

    public override fun gameOn() {

        JJ.scenes.addSceneSpec("circle", circle())
        JJ.scenes.addSceneSpec("terrain", floor())

        JJ.scenes.instantiate(
                SceneSpec {
                    nodeRef(params {
                        x = 0f; y = 8f
                    }, "circle")
                    nodeRef(params {
                        x = 0f; y = 0f; scaleX = 20f
                        prop("material", "glass")
                    }, "terrain")
                }
        )
    }

    private fun circle(): SceneSpec {
        return SceneSpec {
            physics {
                bodyType = BodyDef.BodyType.DynamicBody
                fixture {
                    restitution = 0.7f
                    shape = Circle()
                }
            }
        }
    }

    private fun floor(): SceneSpec {
        return SceneSpec {
            physics {
                bodyType = BodyDef.BodyType.StaticBody
                fixture {
                }
            }
            render {
                //TODO: Allow adding of scene2d like specs to a scene.
                //scene2D()
            }
        }
    }
}
