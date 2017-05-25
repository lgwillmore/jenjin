package com.binarymonks.jj.core.extensions

import com.badlogic.gdx.math.Vector2
import com.binarymonks.jj.core.pools.new


fun Vector2.copy():Vector2{
    return new(Vector2::class).set(this)
}

