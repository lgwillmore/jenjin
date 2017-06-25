package com.binarymonks.jj.core.workshop

import box2dLight.Light
import box2dLight.PointLight
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.audio.SoundEffects
import com.binarymonks.jj.core.audio.SoundParams
import com.binarymonks.jj.core.extensions.copy
import com.binarymonks.jj.core.physics.PhysicsNode
import com.binarymonks.jj.core.physics.PhysicsRoot
import com.binarymonks.jj.core.pools.mat3
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.recycle
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.render.RenderRoot
import com.binarymonks.jj.core.render.nodes.RenderNode
import com.binarymonks.jj.core.scenes.Scene
import com.binarymonks.jj.core.specs.*
import com.binarymonks.jj.core.specs.physics.FixtureSpec
import com.binarymonks.jj.core.specs.physics.LightSpec
import com.binarymonks.jj.core.specs.physics.PhysicsSpec
import com.binarymonks.jj.core.specs.physics.PointLightSpec
import com.binarymonks.jj.core.specs.render.RenderSpec

class MasterFactory {

    private var paramsStackCache: Array<ParamStack> = Array()
    internal var scenePool: ScenePool = ScenePool()

    fun createScene(scene: SceneSpec, params: InstanceParams): Scene {
        var paramsStack = paramsStack()
        paramsStack.add(params)
        var myScene: Scene = createSceneHelper(scene, paramsStack)
        returnParamsStack(paramsStack)
        return myScene
    }

    private fun createSceneHelper(
            scene: SceneSpec,
            paramsStack: ParamStack): Scene {

        val myScene = createSceneCore(scene, paramsStack)

        val things = ObjectMap<String, Scene>()
        for (entry in scene.nodes) {
            val nodeScene = checkNotNull(entry.value.sceneRef).resolve()
            val nodeParams = entry.value.instanceParams
            paramsStack.add(nodeParams)
            val nodeThing = createSceneHelper(nodeScene, paramsStack)
            things.put(entry.key, nodeThing)
            myScene.addChild(nodeThing)
            paramsStack.pop()
        }
        for (jointSpec in scene.joints) {
            val bodyA: Body = if (jointSpec.nameA == null) myScene.physicsRoot.b2DBody else things[jointSpec.nameA, myScene].physicsRoot.b2DBody
            val jointDef = jointSpec.toJointDef(
                    bodyA,
                    things[jointSpec.nameB].physicsRoot.b2DBody,
                    paramsStack.transformMatrix
            )
            JJ.B.physicsWorld.b2dworld.createJoint(jointDef)
        }
        return myScene
    }

    private fun createSceneCore(sceneSpec: SceneSpec, paramsStack: ParamStack): Scene {
        if (sceneSpec.isPooled) {
            val scene: Scene? = scenePool.get(paramsStack.scaleX, paramsStack.scaleY, sceneSpec.id)
            if (scene != null) {
                scene.resetFromPool(paramsStack.x, paramsStack.y, paramsStack.rotationD)
                scene.uniqueName = paramsStack.peek().uniqueInstanceName
                scene.name = paramsStack.peek().name
                return scene
            }
        }
        val thing = Scene(
                paramsStack.peek().name,
                paramsStack.peek().uniqueInstanceName,
                physicsRoot = buildPhysicsRoot(sceneSpec.physics, paramsStack),
                renderRoot = buildRenderRoot(sceneSpec.render, paramsStack),
                soundEffects = buildSoundEffects(sceneSpec.sounds),
                properties = paramsStack.peek().properties.copy(),
                pooled = sceneSpec.isPooled
        )
        addBehaviour(thing, sceneSpec)

        JJ.B.renderWorld.addThing(thing)
        JJ.B.sceneWorld.add(thing)

        return thing
    }

    private fun addBehaviour(scene: Scene, sceneSpec: SceneSpec) {
        for (component in sceneSpec.components) {
            scene.addComponent(component.clone())
        }
    }

    private fun buildSoundEffects(sounds: Array<SoundParams>): SoundEffects {
        val soundEffects = SoundEffects()
        soundEffects.addSoundEffects(sounds)
        return soundEffects
    }

    private fun buildRenderRoot(renderSpec: RenderSpec, paramsStack: ParamStack): RenderRoot {
        val renderRoot: RenderRoot = RenderRoot(renderSpec.id)
        for (nodeSpec in renderSpec.renderNodes) {
            val node: RenderNode = nodeSpec.makeNode(paramsStack)
            renderRoot.addNode(nodeSpec.layer, node)
        }
        return renderRoot
    }

    private fun buildPhysicsRoot(physicsSpec: PhysicsSpec, paramsStack: ParamStack): PhysicsRoot {
        val def = BodyDef()

        var worldPosition = vec2().mul(paramsStack.transformMatrix)

        def.position.set(worldPosition.x, worldPosition.y)
        def.angle = paramsStack.rotationD * MathUtils.degreesToRadians
        def.type = physicsSpec.bodyType
        def.fixedRotation = physicsSpec.fixedRotation
        def.linearDamping = physicsSpec.linearDamping
        def.angularDamping = physicsSpec.angularDamping
        def.bullet = physicsSpec.bullet
        def.allowSleep = physicsSpec.allowSleep
        def.gravityScale = physicsSpec.gravityScale
        val body = JJ.B.physicsWorld.b2dworld.createBody(def)

        val physicsRoot = PhysicsRoot(body)

        for (ibegin in physicsSpec.beginCollisions) {
            physicsRoot.collisionResolver.addInitialBegin(ibegin.clone())
        }
        for (fbegin in physicsSpec.finalBeginCollisions) {
            physicsRoot.collisionResolver.addFinalBegin(fbegin.clone())
        }
        for (end in physicsSpec.endCollisions) {
            physicsRoot.collisionResolver.addInitialBegin(end.clone())
        }

        for (fixtureSpec in physicsSpec.fixtures) {
            buildFixture(physicsRoot, fixtureSpec, body, paramsStack)
        }

        for (lightSpec in physicsSpec.lights) {
            val light = buildLight(lightSpec, paramsStack.peek())
            light.attachToBody(body)
            if (lightSpec.name != null) {
                physicsRoot.lights.add(checkNotNull(lightSpec.name), light)
            } else {
                physicsRoot.lights.add(light)
            }
        }

        return physicsRoot

    }

    private fun buildLight(lightSpec: LightSpec, instanceParams: InstanceParams): Light {
        when (lightSpec) {
            is PointLightSpec -> return buildPointLight(lightSpec, instanceParams)
            else -> throw Exception("Don't know that light")
        }
    }

    private fun buildPointLight(pointSpec: PointLightSpec, instanceParams: InstanceParams): Light {
        return PointLight(
                JJ.B.renderWorld.rayHandler,
                pointSpec.rays, pointSpec.color.copy().get(instanceParams), pointSpec.reach, pointSpec.offsetX, pointSpec.offsetY)
    }

    private fun buildFixture(physicsRoot: PhysicsRoot, fixtureSpec: FixtureSpec, body: Body, params: ParamStack) {
        val shape = buildShape(fixtureSpec, params.scaleX, params.scaleY, fixtureSpec.offsetX, fixtureSpec.offsetY)
        val fDef = FixtureDef()
        fDef.shape = shape
        val material = JJ.physics.materials.getMaterial(fixtureSpec.material.get(params.peek()))
        fDef.density = material?.density ?: fixtureSpec.density
        fDef.friction = material?.friction ?: fixtureSpec.friction
        fDef.restitution = material?.restitution ?: fixtureSpec.restitution
        fDef.isSensor = fixtureSpec.isSensor
        val cd = fixtureSpec.collisionGroup.toCollisionData(checkNotNull(params.peek()).properties)
        fDef.filter.categoryBits = cd.category
        fDef.filter.maskBits = cd.mask
        val fixture = body.createFixture(fDef)
        val physicsNode = PhysicsNode(fixture, physicsRoot, material?.name)
        fixture.userData = physicsNode
        shape!!.dispose()
    }

    private fun buildShape(nodeSpec: FixtureSpec, scaleX: Float, scaleY: Float, offsetX: Float = 0f, offsetY: Float = 0f): Shape? {
        if (nodeSpec.shape is Rectangle) {
            val polygonRectangle = nodeSpec.shape as Rectangle
            val boxshape = PolygonShape()
            boxshape.setAsBox(polygonRectangle.width * scaleX / 2.0f, polygonRectangle.height * scaleY / 2.0f, new(Vector2::class.java).set(nodeSpec.offsetX * scaleX, nodeSpec.offsetY * scaleY), nodeSpec.rotationD * MathUtils.degreesToRadians)
            return boxshape
        } else if (nodeSpec.shape is Circle) {
            val circle = nodeSpec.shape as Circle
            val circleShape = CircleShape()
            circleShape.radius = circle.radius * scaleX
            circleShape.position = vec2().set(nodeSpec.offsetX * scaleX, nodeSpec.offsetY * scaleY)
            return circleShape
        } else if (nodeSpec.shape is Polygon) {
            val trans = mat3()
            trans.scale(scaleX, scaleY)
            trans.translate(offsetX, offsetY)
            trans.rotate(nodeSpec.rotationD)
            val polygonSpec = nodeSpec.shape as Polygon
            val polygonShape = PolygonShape()
            val vertices = arrayOfNulls<Vector2>(polygonSpec.vertices.size)
            for (i in 0..polygonSpec.vertices.size - 1) {
                vertices[i] = polygonSpec.vertices.get(i).copy().mul(trans)
            }
            polygonShape.set(vertices)
            recycle(trans)
            return polygonShape
        } else if (nodeSpec.shape is Chain) {
            val trans = mat3()
            trans.scale(scaleX, scaleY)
            trans.translate(offsetX, offsetY)
            trans.rotate(nodeSpec.rotationD)
            val chainSpec = nodeSpec.shape as Chain
            val chainShape = ChainShape()
            val vertices = arrayOfNulls<Vector2>(chainSpec.vertices.size)
            for (i in 0..chainSpec.vertices.size - 1) {
                vertices[i] = chainSpec.vertices.get(i).copy().mul(trans)
            }
            chainShape.createChain(vertices)
            recycle(trans)
            return chainShape
        }
        return null
    }

    private fun paramsStack(): ParamStack {
        if (paramsStackCache.size > 0) {
            return paramsStackCache.pop()
        }
        return ParamStack()
    }

    private fun returnParamsStack(stack: ParamStack) {
        stack.clear()
        stack.rotationD = 0f
        stack.x = 0f
        stack.y = 0f
        stack.scaleX = 1f
        stack.scaleY = 1f
        stack.transformMatrix.idt()
        paramsStackCache.add(stack)
    }


}


class ParamStack : Array<InstanceParams>() {

    var rotationD = 0f
    var x = 0f
    var y = 0f
    var scaleX = 1f
    var scaleY = 1f
    var transformMatrix: Matrix3 = mat3()

    override fun add(params: InstanceParams) {
        super.add(params)
        rotationD += params.rotationD
        x += params.x
        y += params.y
        scaleX *= params.scaleX
        scaleY *= params.scaleY
        transformMatrix.mul(params.getTransformMatrix())
    }

    override fun pop(): InstanceParams? {
        var removing = super.pop()
        if (removing != null) {
            rotationD -= removing.rotationD
            x -= removing.x
            y -= removing.y
            scaleX /= removing.scaleX
            scaleY /= removing.scaleY
            transformMatrix.mul(removing.getTransformMatrix().inv())
        }
        return removing
    }
}