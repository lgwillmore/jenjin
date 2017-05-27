package com.binarymonks.jj.core.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Transform
import com.binarymonks.jj.core.physics.collisions.CollisionResolver
import com.binarymonks.jj.core.things.Thing

class PhysicsRoot(val b2DBody: Body) {
    var parent: Thing? = null
        set(value){
            field = value
            b2DBody.userData=value
            collisionResolver.parent=value
        }

    var collisionResolver: CollisionResolver = CollisionResolver()

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

    fun hasProperty(propertyKey: String): Boolean {
        if (parent != null) return parent!!.hasProperty(propertyKey)
        return false
    }

}

