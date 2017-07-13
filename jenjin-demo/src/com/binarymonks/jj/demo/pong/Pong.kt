package com.binarymonks.jj.demo.pong

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.JJConfig
import com.binarymonks.jj.core.JJGame
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.layers.Layer
import com.binarymonks.jj.core.physics.collisions.SoundCollision
import com.binarymonks.jj.core.specs.Circle
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.ui.JJClickListener
import com.binarymonks.jj.core.ui.UIBuilder

val COURT_LENGTH = 12f
val BAT_LENGTH = 1.5f

object PongConfig {
    var jjConfig: JJConfig = JJConfig()

    init {
        jjConfig.b2d.gravity = Vector2.Zero

        jjConfig.gameView.worldBoxWidth = COURT_LENGTH
        jjConfig.gameView.cameraPosX = 6f
        jjConfig.gameView.cameraPosY = 6f
    }
}

class Pong : JJGame(PongConfig.jjConfig) {

    override fun gameOn() {

        // Load assets
        JJ.assets.loadNow("ui/uiskin.json", Skin::class)

        // Layers
        JJ.layers.registerLayer("mainMenu", mainMenu())


        // Scenes
        JJ.scenes.addSceneSpec("ball", ball())
        JJ.scenes.addSceneSpec("player", player())
        JJ.scenes.addSceneSpec("court", court())
        JJ.scenes.loadAssetsNow()

        JJ.scenes.instantiate("court")

        JJ.layers.push("mainMenu")
    }
}

fun court(): SceneSpecRef {
    return scene {
        //LeftWall
        node(params { x = 0f; y = COURT_LENGTH / 2 }) {
            physics {
                bodyType = BodyDef.BodyType.StaticBody
                fixture { shape = Rectangle(1f, COURT_LENGTH) }
            }
            render { rectangleRender(1f, COURT_LENGTH) }
        }
        //RightWall
        node(params { x = COURT_LENGTH; y = COURT_LENGTH / 2 }) {
            physics {
                bodyType = BodyDef.BodyType.StaticBody
                fixture { shape = Rectangle(1f, COURT_LENGTH) }
            }
            render { rectangleRender(1f, COURT_LENGTH) }
        }
        //BottomWall
        node(params { x = COURT_LENGTH / 2; y = 0f; rotationD = 90f }) {
            physics {
                bodyType = BodyDef.BodyType.StaticBody
                fixture { shape = Rectangle(1f, COURT_LENGTH) }
            }
            render { rectangleRender(1f, COURT_LENGTH) }
        }
        //TopWall
        node(params { x = COURT_LENGTH / 2; y = COURT_LENGTH; rotationD = 90f }) {
            physics {
                bodyType = BodyDef.BodyType.StaticBody
                fixture { shape = Rectangle(1f, COURT_LENGTH) }
            }
            render { rectangleRender(1f, COURT_LENGTH) }
        }
    }
}


fun player(): SceneSpecRef {

    return scene {
        physics {
            bodyType = BodyDef.BodyType.KinematicBody
            fixture { shape = Rectangle(0.3f, BAT_LENGTH) }
        }
        render { rectangleRender(0.3f, BAT_LENGTH) }
        component(Player())
    }
}

class Player : Component() {
    internal var velocity = 30f

    internal var up = false
    internal var down = false

    override fun update() {
        var direction = 0f
        val y = me().physicsRoot.position().y
        if (up && y < COURT_LENGTH - BAT_LENGTH / 2) {
            direction += 1f
        }
        if (down && y > BAT_LENGTH / 2) {
            direction -= 1f
        }
        me().physicsRoot.b2DBody.setLinearVelocity(0f, velocity * direction)
    }

    fun goUp() = { up = true }

    fun stopUp() = { up = false }

    fun goDown() = { down = true }

    fun stopDown() = { down = false }
}

fun ball(): SceneSpecRef {
    val ballRadius = 0.15f
    return scene {
        sound("bounce", "sounds/pong.mp3") {
            volume = 0.6f
        }
        physics {
            bodyType = BodyDef.BodyType.DynamicBody
            fixture {
                shape = Circle(ballRadius)
                restitution = 0.9f
            }
            collisions.begin(SoundCollision(soundName = "bounce"))
        }
        render {
            circleRender(ballRadius)
        }
    }
}

private fun newGame() {
    //Player A
    JJ.scenes.instantiate(params { x = 1f; y = COURT_LENGTH / 2 }, "player").then {

    }
    //Player B
    JJ.scenes.instantiate(params { x = COURT_LENGTH - 1f; y = COURT_LENGTH / 2 }, "player").then {

    }
    newBall()
}

private fun newBall(direction: Int = 1) {
    JJ.scenes.instantiate(params { x = COURT_LENGTH / 2; y = COURT_LENGTH / 2 }, "ball").then {
        it.physicsRoot.b2DBody.setLinearVelocity(5f * direction, 0f)
    }
}

fun mainMenu(): Layer {
    val skin = JJ.assets.getAsset("ui/uiskin.json", Skin::class)
    val vWidth = 400f
    val vHeight = 400f
    return UIBuilder(ExtendViewport(vWidth, vHeight)) {

        actor(Label("PIXEL PONG", skin)) {
            x = vWidth * 0.4f
            y = vHeight * 2 / 3
        }

        actor(TextButton("START", skin)) {
            x = vWidth * 0.45f
            y = vHeight * 0.5f
        }.withListener(object : JJClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                JJ.layers.pop()
                newGame()
            }
        })
    }.build()
}