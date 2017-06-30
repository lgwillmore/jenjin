package com.binarymonks.jj.core.physics

import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.utils.ObjectMap


class PhysicsNode(var name: String?, var fixture: Fixture, var physicsRoot: PhysicsRoot, var material: String?) {
    var properties: ObjectMap<String, Any> = ObjectMap()
    val collisionResolver: CollisionResolver = CollisionResolver()

    init {
        collisionResolver.parent = physicsRoot.collisionResolver
        physicsRoot.addNode(this)
    }

    fun hasProperty(propertyKey: String): Boolean {
        if (properties.containsKey(propertyKey)) return true
        return physicsRoot.hasProperty(propertyKey)
    }

    fun getProperty(propertyKey: String): Any?{
        if (properties.containsKey(propertyKey)) return properties[propertyKey]
        return physicsRoot.getProperty(propertyKey)
    }
}