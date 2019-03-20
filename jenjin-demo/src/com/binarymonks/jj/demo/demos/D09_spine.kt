package com.binarymonks.jj.demo.demos

import com.badlogic.gdx.math.Vector2
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.JJGame
import com.binarymonks.jj.core.audio.SoundMode
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.core.specs.params
import com.binarymonks.jj.spine.components.SpineComponent
import com.binarymonks.jj.spine.specs.SpineSpec
import com.esotericsoftware.spine.Event


class D09_spine : JJGame(JJConfig {
    b2d.debugRender = true
    b2d.gravity = Vector2()
    gameView.worldBoxWidth = 4f
    gameView.cameraPosX = 0f
    gameView.cameraPosY = 0f
}) {
    override fun gameOn() {
        JJ.scenes.addSceneSpec("spineBoy", spineBoy())
        JJ.scenes.loadAssetsNow()

        JJ.scenes.instantiate(SceneSpec {
            node(params { x=-0.7f; }, "spineBoy" )
            node(params { x=0.7f; }, "spineBoy" )
        })
    }

    fun spineBoy(): SceneSpecRef {
        return SpineSpec {
            spineRender {
                atlasPath = "spine/spineboy/spineboy-pma.atlas"
                dataPath = "spine/spineboy/spineboy.json"
                scale = 1 / 247f
                originY = 247f
            }
            animations {
                defaultMix=0.5f
                startingAnimation="idle"
                setMix("walk", "run", 0.4f)
                setMix( "run", "walk",0.5f)
                registerEventHandler("footstep", { component, _ ->
                    component.me().soundEffects.triggerSound("footstep", SoundMode.NORMAL)
                })
                registerEventHandler("footstep", SpineBoyComponent::class, SpineBoyComponent::onEvent)
                registerEventFunction("footstep", SpineBoyComponent::class, SpineBoyComponent::step)
            }
            skeleton {  }
            rootScene {
                component(SpineBoyComponent())
                sounds.sound("footstep", "sounds/step.mp3")
            }
        }
    }
}

class SpineBoyComponent : Component() {

    var nextTransition = "walk"
    var scheduledID = -1

    override fun onAddToWorld() {
        scheduledID= JJ.clock.schedule(this::transition,delaySeconds = 3f, repeat = 0)
    }

    override fun onRemoveFromWorld() {
        JJ.clock.cancel(scheduledID)
    }

    fun onEvent(event: Event) {
        println("Just stepped for event: ${event.data.name}")
    }

    fun step() {
        println("Just stepped because I was told to")
    }

    fun transition(){
        when(nextTransition){
            "walk" -> {
                me().getComponent(SpineComponent::class)[0].transitionToAnimation("walk")
                nextTransition="run"
            }
            "run" -> {
                me().getComponent(SpineComponent::class)[0].transitionToAnimation("run")
                nextTransition="walk"
            }
            else -> println("Confused")
        }
    }

}