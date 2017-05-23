package com.binarymonks.jj.core.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Transform

class PhysicsRoot(val b2DBody: Body) {

    fun position(): Vector2 {
        return b2DBody.position
    }

    fun rotationR(): Float {
        return b2DBody.angle
    }

    fun setPosition(x: Float, y: Float) {
        b2DBody.setTransform(x, y, b2DBody.angle)
    }

    fun setPosition(position: Vector2) {
        b2DBody.setTransform(position.x, position.y, b2DBody.angle)
    }

    fun setRotationR(rotation: Float) {
        val position = b2DBody.position
        b2DBody.setTransform(position.x, position.y, rotation)
    }

    val transform: Transform
        get() = b2DBody.transform
}

