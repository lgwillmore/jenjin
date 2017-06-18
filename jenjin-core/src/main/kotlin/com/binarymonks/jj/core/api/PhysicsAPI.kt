package com.binarymonks.jj.core.api

import com.binarymonks.jj.core.physics.CollisionGroups
import com.binarymonks.jj.core.physics.Materials


interface PhysicsAPI {
    var collisionGroups: CollisionGroups
    var materials: Materials
}