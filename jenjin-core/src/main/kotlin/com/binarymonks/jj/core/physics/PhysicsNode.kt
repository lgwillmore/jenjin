package com.binarymonks.jj.core.physics

import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.utils.ObjectMap


class PhysicsNode(var fixture: Fixture, var physicsRoot: PhysicsRoot) {
    var properties: ObjectMap<String, Any> = ObjectMap()

    fun hasProperty(propertyKey: String): Boolean {
        if (properties.containsKey(propertyKey)) return true
        return physicsRoot.hasProperty(propertyKey)
    }
}