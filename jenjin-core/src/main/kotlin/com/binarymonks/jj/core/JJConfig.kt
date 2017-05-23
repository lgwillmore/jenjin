package com.binarymonks.jj.core

class JJConfig {
    var b2dDebug = false
    var gameViewConfig = GameViewConfig()

    class GameViewConfig {
        var worldBoxWidth = 50f
        var cameraPosX: Float = 0f
        var cameraPosY: Float = 0f
    }
}
