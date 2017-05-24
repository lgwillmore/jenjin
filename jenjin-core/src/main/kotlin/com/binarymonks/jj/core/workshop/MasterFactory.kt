package com.binarymonks.jj.core.workshop

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.physics.PhysicsRoot
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.recycle
import com.binarymonks.jj.core.specs.InstanceParams
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.ThingSpec
import com.binarymonks.jj.core.specs.physics.*
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

        for (fixtureSpec in physicsSpec.fixtures){
            buildFixture(fixtureSpec,body,params, paramsStack)
        }

        return PhysicsRoot(body)
    }

    private fun buildFixture(fixtureSpec: FixtureSpec, body: Body, instanceParams: InstanceParams, paramsStack: Array<InstanceParams>) {
            val shape = buildShape(fixtureSpec, instanceParams, paramsStack)
            val fDef = FixtureDef()
            fDef.shape = shape
            fDef.density = fixtureSpec.density
            fDef.friction = fixtureSpec.friction
            fDef.restitution = fixtureSpec.restitution
            fDef.isSensor = fixtureSpec.isSensor
//            val cd = Global.physics.collisionGroups.getCollisionData(fixtureSpec.collisionData)
//            fDef.filter.categoryBits = cd.category
//            fDef.filter.maskBits = cd.mask
//            Re.cycle(cd)

            val f = body.createFixture(fDef)
//            node.fixture = f
//            f.userData = node

//            val resolver = CollisionResolver()
//            resolver.setSelf(thing)
//            for (ibegin in fixtureSpec.initialBeginCollisions) {
//                resolver.addInitialBegin(ibegin.clone())
//            }
//            for (fbegin in fixtureSpec.finalBeginCollisions) {
//                resolver.addFinalBegin(fbegin.clone())
//            }
//            for (end in fixtureSpec.endCollisions) {
//                resolver.addInitialBegin(end.clone())
//            }
//
//            node.collisionResolver = resolver

            shape!!.dispose()
    }

    private fun buildShape(nodeSpec: FixtureSpec, instanceParams: InstanceParams, paramsStack: Array<InstanceParams>): Shape? {
        if (nodeSpec.shape is Rectangle) {
            val polygonRectangle = nodeSpec.shape as Rectangle
            val boxshape = PolygonShape()
            boxshape.setAsBox(polygonRectangle.width * instanceParams.scaleX / 2.0f, polygonRectangle.height * instanceParams.scaleY / 2.0f, new(Vector2::class.java).set(nodeSpec.offsetX * instanceParams.scaleX, nodeSpec.offsetY * instanceParams.scaleY), nodeSpec.rotationD * MathUtils.degreesToRadians)
            return boxshape
        } else if (nodeSpec.shape is Circle) {
            val circle = nodeSpec.shape as Circle
            val circleShape = CircleShape()
            circleShape.radius = circle.radius* instanceParams.scaleX
            circleShape.position = new(Vector2::class).set(nodeSpec.offsetX, nodeSpec.offsetY)
            return circleShape
        } else if (nodeSpec.shape is Polygon) {
            val polygonSpec = nodeSpec.shape as Polygon
            val polygonShape = PolygonShape()
            val vertices = arrayOfNulls<Vector2>(polygonSpec.vertices.size)
            for (i in 0..polygonSpec.vertices.size - 1) {
                vertices[i] = polygonSpec.vertices.get(i)
            }
            polygonShape.set(vertices)
            return polygonShape
        } else if (nodeSpec.shape is Chain) {
            val chainSpec = nodeSpec.shape as Chain
            val chainShape = ChainShape()
            val vertices = arrayOfNulls<Vector2>(chainSpec.vertices.size)
            for (i in 0..chainSpec.vertices.size - 1) {
                vertices[i] = chainSpec.vertices.get(i).add(nodeSpec.offsetX, nodeSpec.offsetY)
            }
            chainShape.createChain(vertices)
            return chainShape
        }
        return null
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