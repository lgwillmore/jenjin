package com.binarymonks.jj.demo.demos

import com.badlogic.gdx.math.Vector2
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.JJGame
import com.binarymonks.jj.core.audio.SoundMode
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.core.specs.builders.nodeRef
import com.binarymonks.jj.core.specs.builders.scene
import com.binarymonks.jj.core.spine.specs.SpineSpec
import com.esotericsoftware.spine.Event


class D09_spine : JJGame(JJConfig {
    b2d.debug = false
    b2d.gravity = Vector2()
    gameView.worldBoxWidth = 4f
    gameView.cameraPosX = 0f
    gameView.cameraPosY = 0f
}) {
    override fun gameOn() {
        JJ.scenes.addSceneSpec("spineBoy", spineBoy())
        JJ.scenes.loadAssetsNow()

        JJ.scenes.instantiate(scene {
            nodeRef { "spineBoy" }
        })
    }

    fun spineBoy(): SceneSpecRef {

        return SpineSpec {
            atlasPath = "spine/spineboy/spineboy-pma.atlas"
            dataPath = "spine/spineboy/spineboy.json"
            animation.startingAnimation = "run"
            scale = 1f / 247f
            originY = 247f
            sounds.sound("footstep", "sounds/step.mp3")
            animation.registerEventHandler("footstep", {
                component, event ->
                component.me().soundEffects.triggerSound("footstep", SoundMode.NORMAL)
            })
            animation.registerEventHandler("footstep", ArbitraryComponent::class, ArbitraryComponent::onEvent)
            animation.registerEventFunction("footstep", ArbitraryComponent::class, ArbitraryComponent::step)
            rootComponents.add(ArbitraryComponent())
        }
    }
}

class ArbitraryComponent : Component() {

    fun onEvent(event: Event) {
        println("Just stepped for event: ${event.data.name}")
    }

    fun step() {
        println("Just stepped because I was told to")
    }

}