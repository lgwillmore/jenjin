package com.binarymonks.jj.core.extensions

import com.badlogic.gdx.math.Vector2
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.vec2


fun Vector2.copy(): Vector2 {
    return vec2().set(this)
}

