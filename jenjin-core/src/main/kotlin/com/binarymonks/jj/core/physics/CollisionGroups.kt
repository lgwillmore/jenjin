package com.binarymonks.jj.core.physics

import com.badlogic.gdx.utils.ObjectMap

/**
 * This is needed to check Collision groups of your game objects. This will determine what collides with what.
 */
class CollisionGroups {

    private var groups: ObjectMap<String, CollisionGroup> = ObjectMap()

    fun addGroup(group: CollisionGroup) {
        groups.put(group.name, group)
    }

    fun getCollisionData(name: String): CollisionData {
        if (groups.containsKey(name)) {
            return groups.get(name).collisionData
        }
        throw Exception("No Collision group for $name")
    }

}
