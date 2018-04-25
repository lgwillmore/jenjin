package com.binarymonks.jj.spine.components

import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.audio.SoundMode
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.mockoutGDXinJJ
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.spine.specs.SpineAnimations
import com.binarymonks.jj.spine.specs.SpineSpec
import com.esotericsoftware.spine.Event
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class SpineComponentTest {

    @Before
    fun setUp(){
        mockoutGDXinJJ()
    }

    @Test
    fun testClone(){
        val original = SpineComponent(SpineAnimations())
        original.clone()
    }
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
        rootScene {
            component(SpineBoyComponent())
            sounds.sound("footstep", "sounds/step.mp3")
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