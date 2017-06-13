package com.binarymonks.jj.demo

import com.badlogic.gdx.math.Vector2
import com.binarymonks.jj.core.Game
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.builders.nodeRef
import com.binarymonks.jj.core.specs.builders.scene
import com.binarymonks.jj.core.spine.specs.SpineSpec


class D09_spine : Game(MyConfig09.jjConfig) {
    override fun gameOn() {
        JJ.scenes.addSceneSpec("spineBoy", spineBoy())
        JJ.scenes.loadAssetsNow()

        JJ.scenes.instantiate(scene {
            nodeRef { "spineBoy" }
        })
    }

    fun spineBoy(): SceneSpec {
        return SpineSpec {
            atlasPath = "spine/spineboy/spineboy-pma.atlas"
            dataPath = "spine/spineboy/spineboy.json"
            startingAnimation = "walk"
            scale = 1f / 247f
            originY = 247f
        }.toSceneSpec()
    }
}

object MyConfig09 {
    var jjConfig: JJConfig = JJConfig()

    init {
        jjConfig.b2d.debug = false
        jjConfig.b2d.gravity = Vector2()

        jjConfig.gameView.worldBoxWidth = 4f
        jjConfig.gameView.cameraPosX = 0f
        jjConfig.gameView.cameraPosY = 0f
    }
}