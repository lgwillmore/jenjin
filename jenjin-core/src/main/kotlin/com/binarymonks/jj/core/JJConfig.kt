package com.binarymonks.jj.core

import com.badlogic.gdx.math.Vector2

class JJConfig {
    var gameViewConfig = GameViewConfig()
    var b2dConfig = B2DConfig()
}

class GameViewConfig {
    var worldBoxWidth = 50f
    var cameraPosX: Float = 0f
    var cameraPosY: Float = 0f
}

class B2DConfig{
    var debug = false
    var gravigy = Vector2(0f,-9f)
}
