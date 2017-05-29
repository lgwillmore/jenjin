package com.binarymonks.jj.core.specs.physics

import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.ShapeSpec

class FixtureSpec {
    var density = 0.5f
    var friction = 0.1f
    var restitution = 0f
    var rotationD = 0f
    var offsetX = 0f
    var offsetY = 0f
    var isSensor = false
    var collisionGroup: CollisionGroupSpec = CollisionGroupSpecExplicit()
        private set
    var shape: ShapeSpec = Rectangle()

    /**
     * Puts the fixture in a collision group by name.
     * This must be set  in [com.binarymonks.jj.core.JJ.physics.collisionGroups]
     */
    fun collsionGroup(name: String) {
        this.collisionGroup = CollisionGroupSpecName(name)
    }

    /**
     * Set the collision mask and category to explicit values.
     */
    fun collisionGroupExplicit(category: Short, mask: Short) {
        this.collisionGroup = CollisionGroupSpecExplicit(category, mask)
    }

    /**
     * Works the same as [collisionGroup], but the name of the group is retrieved from a property
     *
     * This is evaluated at instantiation only. Not constantly.
     */
    fun collisionGroupProperty(propertyName: String) {
        this.collisionGroup = CollisionGroupSpecProperty(propertyName)
    }
}