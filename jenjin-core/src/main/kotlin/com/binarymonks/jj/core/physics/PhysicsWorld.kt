package com.binarymonks.jj.core.physics

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.api.PhysicsAPI

class PhysicsWorld : PhysicsAPI {

    var b2dworld: World = World(JJ.B.config.b2dConfig.gravity, true)
    var velocityIterations = 10
    var positionIterations = 20
    override var collisionGroups = CollisionGroups()
    var isUpdating = false
        internal set
    val worldBody: Body

    init {
        b2dworld.setContactListener(JJContactListener())
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        bodyDef.position.set(0f, 0f)
        worldBody = b2dworld.createBody(bodyDef)
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
