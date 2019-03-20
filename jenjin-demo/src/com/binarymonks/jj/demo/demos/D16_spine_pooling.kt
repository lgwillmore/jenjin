package com.binarymonks.jj.demo.demos

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.JJGame
import com.binarymonks.jj.core.audio.SoundMode
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.components.misc.Emitter
import com.binarymonks.jj.core.components.misc.SelfDestruct
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.core.specs.params
import com.binarymonks.jj.spine.components.SpineComponent
import com.binarymonks.jj.spine.specs.SpineSpec


class D16_spine_pooling : JJGame(JJConfig {
    b2d.debugRender = true
    val width = 6f
    gameView.worldBoxWidth = width
    gameView.cameraPosX = 0f
    gameView.cameraPosY = 2f
    gameView.clearColor = Color(0.2f, 0.2f, 0.2f, 1f)
}) {

    override fun gameOn() {
        JJ.scenes.addSceneSpec("spineBounding", spine_with_bounding_boxes())
        JJ.scenes.addSceneSpec("spineAnimation", spine_with_animations())
        JJ.scenes.addSceneSpec("spawnBounding", spawnerBounding())
        JJ.scenes.addSceneSpec("spawnAnimation", spawnerAnimated())
        JJ.scenes.loadAssetsNow()

        JJ.scenes.instantiate(SceneSpec {
            container = true
            node(params { x = -2f }, "spawnBounding")
            node(params { x = 2f }, "spawnAnimation")
            node(params{x=-2f; y=-0.18f}) {
                physics {
                    bodyType=BodyDef.BodyType.StaticBody
                    fixture {
                        shape=Rectangle(5f,0.3f)
                    }
                }
                render {
                    rectangleRender(5f,0.3f)
                }
            }
        })

    }

    fun spawnerBounding(): SceneSpecRef {
        return SceneSpec {
            component(Emitter()) {
                setSpec("spineBounding")
                delayMinSeconds.set(3f)
                delayMaxSeconds.set(3f)
                repeat.set(0)
            }
        }
    }

    fun spawnerAnimated(): SceneSpecRef {
        return SceneSpec {
            component(Emitter()) {
                setSpec("spineAnimation")
                delayMinSeconds.set(3f)
                delayMaxSeconds.set(3f)
                repeat.set(0)
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
                coreMotorTorque=0f
                coreMotorTorqueFalloff=1f
            }
            rootScene {
                component(SelfDestruct()) {
                    delaySeconds = 2f
                }
                component(DelayedTriggerRagdollComponent())
            }
        }
    }

    fun spine_with_animations(): SceneSpecRef {
        return SpineSpec {
            spineRender {
                atlasPath = "spine/spineboy/spineboy-pma.atlas"
                dataPath = "spine/spineboy/spineboy.json"
                scale = 1 / 247f
                originY = 247f
            }
            animations {
                startingAnimation = "run"
                registerEventHandler("footstep", { component, _ ->
                    component.me().soundEffects.triggerSound("footstep", SoundMode.NORMAL)
                })
                registerEventHandler("footstep", SpineBoyComponent::class, SpineBoyComponent::onEvent)
                registerEventFunction("footstep", SpineBoyComponent::class, SpineBoyComponent::step)
            }
            rootScene {
                component(SpineBoyComponent())
                component(SelfDestruct()) {
                    delaySeconds = 2f
                }
                sounds.sound("footstep", "sounds/step.mp3")
            }
        }
    }
}

class DelayedTriggerRagdollComponent: Component(){

    private var scheduledID = -1

    override fun onAddToWorld() {
        super.onAddToWorld()
        JJ.clock.schedule(this::triggerRagDoll, delaySeconds = 1f)
    }

    fun triggerRagDoll(){
        me().getComponent(SpineComponent::class)[0].triggerRagDoll(1f)
    }

    override fun onRemoveFromWorld() {
        JJ.clock.cancel(scheduledID)
    }
}