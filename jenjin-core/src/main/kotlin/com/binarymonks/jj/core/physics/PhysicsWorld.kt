package com.binarymonks.jj.core.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.api.PhysicsAPI

class PhysicsWorld : PhysicsAPI {

    var b2dworld: World = World(Vector2.Zero, true)
    var velocityIterations = 10
    var positionIterations = 20
//    var collisionGroups: CollisionGroups = CollisionGroupsBasic()
//    internal var postPhysicsFunctions: Array<Function> = Array()

    var isUpdating = false
        internal set

    fun update() {
        isUpdating = true
        val frameDelta = JJ.time.delta
        if (frameDelta > 0) {
            b2dworld.step(JJ.time.deltaFloat, velocityIterations, positionIterations)
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
