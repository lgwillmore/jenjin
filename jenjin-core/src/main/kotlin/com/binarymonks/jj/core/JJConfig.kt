package com.binarymonks.jj.core

import com.badlogic.gdx.math.Vector2

class JJConfig {
    var gameView = GameViewConfig()
    var b2d = B2DConfig()
    var spine = SpineConfig()
}

class GameViewConfig {
    var worldBoxWidth = 50f
    var cameraPosX: Float = 0f
    var cameraPosY: Float = 0f
}

class B2DConfig {
    var debug = false
    var gravity = Vector2(0f, -9f)
}

class SpineConfig {
    var render = true
}