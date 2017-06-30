package com.binarymonks.jj.core.physics

import com.badlogic.gdx.physics.box2d.JointDef
import com.badlogic.gdx.physics.box2d.World
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.api.PhysicsAPI
import com.binarymonks.jj.core.async.OneTimeTask
import com.binarymonks.jj.core.pools.Poolable
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.recycle

open class PhysicsWorld(
        val b2dworld: World = World(JJ.B.config.b2d.gravity, true)
) : PhysicsAPI {


    var velocityIterations = 20
    var positionIterations = 20
    override var collisionGroups = CollisionGroups()
    override var materials = Materials()
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
        isUpdating = false
    }

    /**
     * Lets you create joints during the physics step
     */
    fun createJoint(jointDef: JointDef) {
        if (isUpdating) {
            JJ.tasks.addPostPhysicsTask(new(DelayedCreateJoint::class).set(jointDef))
        } else {
            b2dworld.createJoint(jointDef)
        }
    }

}

internal class DelayedCreateJoint : OneTimeTask(), Poolable {

    var jointDef: JointDef? = null

    fun set(jointDef: JointDef): DelayedCreateJoint {
        this.jointDef = jointDef
        return this
    }

    override fun reset() {
        jointDef = null
    }

    override fun doOnce() {
        JJ.B.physicsWorld.b2dworld.createJoint(jointDef)
        recycle(this)
    }

}
