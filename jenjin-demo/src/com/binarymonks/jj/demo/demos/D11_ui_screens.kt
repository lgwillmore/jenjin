package com.binarymonks.jj.demo.demos

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJGame
import com.binarymonks.jj.core.layers.Layer
import com.binarymonks.jj.core.ui.JJChangeListener
import com.binarymonks.jj.core.ui.UIBuilder


class D11_ui_screens : JJGame() {


    override fun gameOn() {

        JJ.assets.loadNow("ui/uiskin.json", Skin::class)

        JJ.layers.registerLayer("startScreen", startScreen())

        JJ.layers.addLayerTop("startScreen")

    }

    private fun startScreen(): Layer {
        val width = Gdx.graphics.width.toFloat()
        val height = Gdx.graphics.height.toFloat()
        val skin = JJ.assets.getAsset("ui/uiskin.json", Skin::class)

        return UIBuilder {

            actor("beepButton", TextButton("Hide", skin)) {
                setPosition(width / 2, height / 2)
            }.withListener(object : JJChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    myActor?.isVisible = false
                }
            })

            actor(TextButton("Hide", skin)) {
                setPosition(width / 2, height / 2)
            }.withListener(object : JJChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    uiLayer?.getActor("beepButton")?.isVisible = false
                }
            })

            actor(TextButton("Show", skin)) {
                setPosition(width / 2, height / 2)
            }.withListener(object : JJChangeListener() {
                override fun changed(event: ChangeEvent?, actor: Actor?) {
                    uiLayer?.getActor("beepButton")?.isVisible = true
                }
            })

        }.build()
    }

}