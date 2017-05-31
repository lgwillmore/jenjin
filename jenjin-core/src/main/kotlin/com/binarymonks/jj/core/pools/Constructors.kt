package com.binarymonks.jj.core.pools

import com.badlogic.gdx.math.Vector2

/**
 * Some convenience constructors for widely used pooled objects.
 */

fun vec2(x: Float=0f, y: Float=0f): Vector2 {
    return new(Vector2::class).set(x,y)
}


