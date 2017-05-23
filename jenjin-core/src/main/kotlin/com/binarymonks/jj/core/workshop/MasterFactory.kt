package com.binarymonks.jj.core.workshop

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.physics.PhysicsRoot
import com.binarymonks.jj.core.pools.recycle
import com.binarymonks.jj.core.specs.InstanceParams
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.ThingSpec
import com.binarymonks.jj.core.specs.physics.PhysicsSpec
import com.binarymonks.jj.core.things.Thing

class MasterFactory {

    private var paramsStackCache: Array<Array<InstanceParams>> = Array()

    fun createScene(scene: SceneSpec, params: InstanceParams): Thing? {
        var paramsStack = paramsStack()
        paramsStack.add(InstanceParams.new())
        var myThing: Thing? = createSceneHelper(scene, params, paramsStack)
        returnParamsStack(paramsStack)
        return myThing
    }

    private fun createSceneHelper(
            scene: SceneSpec,
            instanceParams: InstanceParams,
            paramsStack: Array<InstanceParams>): Thing? {

        val myThing = createThing(scene.thingSpec, instanceParams, paramsStack)

        for (entry in scene.nodes) {
            val nodeScene = entry.value.resolve()
            val nodeParams = entry.value.params
            paramsStack.add(nodeParams)
            createSceneHelper(nodeScene, nodeParams, paramsStack)
            paramsStack.pop()
        }
        return myThing
    }

    private fun createThing(thingSpec: ThingSpec?, params: InstanceParams, paramsStack: Array<InstanceParams>): Thing? {
        if (thingSpec == null) return null

        val physicsRoot: PhysicsRoot = buildPhysicsRoot(thingSpec.physics, params, paramsStack)

        return Thing(
                params.uniqueInstanceName,
                physicsRoot
        )
    }

    private fun buildPhysicsRoot(physicsSpec: PhysicsSpec, params: InstanceParams, paramsStack: Array<InstanceParams>): PhysicsRoot {
        val def = BodyDef()
        def.position.set(params.x, params.y)
        def.angle = params.rotationD * MathUtils.degreesToRadians
        def.type = physicsSpec.bodyType
        def.fixedRotation = physicsSpec.fixedRotation
        def.linearDamping = physicsSpec.linearDamping
        def.angularDamping = physicsSpec.angularDamping
        def.bullet = physicsSpec.bullet
        def.allowSleep = physicsSpec.allowSleep
        def.gravityScale = physicsSpec.gravityScale
        val body = JJ.B.physicsWorld.b2dworld.createBody(def)
        return PhysicsRoot(body)
    }

    private fun paramsStack(): Array<InstanceParams> {
        if (paramsStackCache.size > 0) {
            return paramsStackCache.pop()
        }
        return Array()
    }

    private fun returnParamsStack(stack: Array<InstanceParams>) {
        stack.forEach { recycle(it) }
        stack.clear()
        paramsStackCache.add(stack)
    }


}