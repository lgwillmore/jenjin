package com.binarymonks.jj.core.physics

import com.badlogic.gdx.physics.box2d.World
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.api.PhysicsAPI

open class PhysicsWorld(
        val b2dworld: World = World(JJ.B.config.b2d.gravity, true)
) : PhysicsAPI {


    var velocityIterations = 10
    var positionIterations = 20
    override var collisionGroups = CollisionGroups()
    var isUpdating = false
        internal set

    init {
        b2dworld.setContactListener(JJContactListener())
    }

    fun update() {
        isUpdating = true
        val frameDelta = JJ.clock.delta
        if (frameDelta > 0) {
            b2dworld.step(JJ.clock.deltaFloat, velocityIterations, positionIterations)
        }
//        for (function in postPhysicsFunctions) {
//            function.call()
//        }
//        postPhysicsFunctions.clear3()
        isUpdating = false
    }

//    fun setCollisionGroups(collisionGroups: CollisionGroups) {
//        this.collisionGroups = collisionGroups
//    }
//
//    fun addPostPhysicsFunction(function: Function) {
//        postPhysicsFunctions.add(function)
//    }

}
