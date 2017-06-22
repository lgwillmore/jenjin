package com.binarymonks.jj.core.spine.collisions

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.Fixture
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.async.OneTimeTask
import com.binarymonks.jj.core.physics.CollisionHandler
import com.binarymonks.jj.core.pools.Poolable
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.recycle
import com.binarymonks.jj.core.spine.components.SpineBoneComponent
import com.binarymonks.jj.core.scenes.Scene

class TriggerRagDollCollision : CollisionHandler() {

    override fun collision(me: Scene, myFixture: Fixture, other: Scene, otherFixture: Fixture, contact: Contact) {
        val boneComponent: SpineBoneComponent = checkNotNull(me.getComponent(SpineBoneComponent::class))
        JJ.tasks.addPostPhysicsTask(new(DelayedTriggerRagDoll::class).set(boneComponent, other))
    }

    override fun clone(): CollisionHandler {
        return TriggerRagDollCollision()
    }

    class DelayedTriggerRagDoll : OneTimeTask(), Poolable {
        internal var spineBone: SpineBoneComponent? = null
        internal var other: Scene? = null

        operator fun set(spineBoneComponent: SpineBoneComponent, other: Scene): DelayedTriggerRagDoll {
            this.spineBone = spineBoneComponent
            this.other = other
            return this
        }

        override fun doOnce() {
            spineBone!!.triggerRagDoll()
            val myBody = spineBone!!.me().physicsRoot.b2DBody
            myBody.gravityScale = 1f
            for (fixture in myBody.getFixtureList()) {
                fixture.setSensor(false)
            }
            recycle(this)
        }

        override fun reset() {
            spineBone = null
            other = null
        }
    }
}
