package com.binarymonks.jj.core.spine.specs

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.assets.AssetReference
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.extensions.copy
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.render.ShaderSpec
import com.binarymonks.jj.core.scenes.ScenePath
import com.binarymonks.jj.core.specs.*
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.physics.FixtureSpec
import com.binarymonks.jj.core.spine.SpineEventHandler
import com.binarymonks.jj.core.spine.components.SpineBoneComponent
import com.binarymonks.jj.core.spine.components.SpineComponent
import com.esotericsoftware.spine.Bone
import com.esotericsoftware.spine.Event
import com.esotericsoftware.spine.Skeleton
import com.esotericsoftware.spine.SkeletonJson
import com.esotericsoftware.spine.attachments.AtlasAttachmentLoader
import com.esotericsoftware.spine.attachments.BoundingBoxAttachment
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2


class SpineSpec() : SceneSpecRef {

    constructor(build: SpineSpec.() -> Unit) : this() {
        this.build()
    }

    private val renderModel = RenderModel()

    private val spineAnimaitons = SpineAnimations()

    private val root = scene { physics { bodyType = BodyDef.BodyType.KinematicBody } }

    private var skel: SpineSkeletonSpec? = null

    fun rootScene(build: SceneSpec.() -> Unit) {
        root.build()
    }

    fun spineRender(build: RenderModel.() -> Unit) {
        renderModel.build()
    }

    fun skeleton(build: SpineSkeletonSpec.() -> Unit) {
        val skel = SpineSkeletonSpec()
        skel.build()
        this.skel = skel
    }

    fun animations(build: SpineAnimations.() -> Unit) {
        spineAnimaitons.build()
    }


    override fun resolve(): SceneSpec {
        val spineComponent = SpineComponent(spineAnimaitons)
        val renderSpec = SpineRenderNodeSpec(
                renderModel.atlasPath,
                renderModel.dataPath,
                renderModel.originX,
                renderModel.originY,
                renderModel.scale,
                renderModel.shader
        )
        renderSpec.layer = renderModel.layer
        renderSpec.priority = renderModel.priority
        root.render.renderNodes.add(renderSpec)
        root.component(spineComponent)
        if (skel != null) {
            val atlas = JJ.assets.getAsset(checkNotNull(renderModel.atlasPath), TextureAtlas::class)
            val atlasLoader = AtlasAttachmentLoader(atlas)
            val json = SkeletonJson(atlasLoader)
            val actualScale = renderModel.scale
            json.scale = actualScale
            val skeletonData = json.readSkeletonData(Gdx.files.internal(renderModel.dataPath))
            val skeleton = Skeleton(skeletonData)
            buildPhysicsSkeleton(root, skeleton, spineComponent)
        }
        return root
    }

    override fun getAssets(): Array<AssetReference> {
        val assets: Array<AssetReference> = root.getAssets()
        assets.add(AssetReference(TextureAtlas::class, checkNotNull(renderModel.atlasPath)))
        return assets
    }

    private fun buildPhysicsSkeleton(scene: SceneSpec, skeleton: Skeleton, spineComponent: SpineComponent) {
        val bone = skeleton.rootBone
        val path: Array<String> = Array()
        path.add(bone.data.name)
        buildBoneRecurse(bone, skel!!.coreMass, skel!!.coreMotorTorque, path, scene, spineComponent, skeleton)
    }

    private fun buildBoneRecurse(bone: Bone, mass: Float, motorTorque: Float, path: Array<String>, parentScene: SceneSpec, spineComponent: SpineComponent, skeleton: Skeleton): String {
        parentScene.addNode(
                scene {
                    physics {
                        bodyType = BodyDef.BodyType.DynamicBody
                        gravityScale = 0f
                        val fixture = buildFixture(bone, mass, skeleton, skel!!.customs.get(path.last()))
                        addFixture(fixture)
                        collisions.copyAppendFrom(skel!!.all.collisions)
                    }
                    component(SpineBoneComponent()) {
                        bonePath = path
                    }
                    bone.children.forEach {
                        val a = path.copy()
                        a.add(it.data.name)
                        val childName = buildBoneRecurse(it, mass * skel!!.massFalloff, motorTorque * skel!!.coreMotorTorqueFalloff, a, this@scene, spineComponent, skeleton)
                        val custom = skel!!.customs.get(childName)
                        revJoint(null, childName, vec2(it.x, it.y), vec2()) {
                            collideConnected = false
                            enableLimit = custom?.boneOverride?.enableLimit ?: skel!!.all.enableLimit
                            lowerAngle = custom?.boneOverride?.lowerAngle ?: skel!!.all.lowerAngle
                            upperAngle = custom?.boneOverride?.upperAngle ?: skel!!.all.upperAngle

                            enableMotor = custom?.boneOverride?.enableMotor ?: skel!!.all.enableMotor
                            motorSpeed = custom?.boneOverride?.motorSpeed ?: 0f
                            maxMotorTorque = custom?.boneOverride?.maxMotorTorque ?: motorTorque
                        }
                    }
                    skel!!.all.properties.forEach {
                        prop(it.key, it.value)
                    }
                    for (component in skel!!.all.components) {
                        this.component(component)
                    }
                },
                params { name = bone.data.name }
        )
        spineComponent.bonePaths.put(bone.data.name, ScenePath(path.copy()))
        return bone.data.name
    }

    private fun buildFixture(bone: Bone, mass: Float, skeleton: Skeleton, customBone: CustomBone?): FixtureSpec {
        if (skel!!.boundingBoxes) {
            val boundingBox: Polygon? = findPolygon(bone, skeleton)
            if (boundingBox != null) {
                return FixtureSpec {
                    shape = customBone?.boneOverride?.shape ?: boundingBox
                    density = customBone?.boneOverride?.mass ?: mass
                    restitution = customBone?.boneOverride?.restitution ?: skel!!.all.restitution
                    friction = customBone?.boneOverride?.friction ?: skel!!.all.friction
                    val mat = customBone?.boneOverride?.material ?: skel!!.all.material
                    if (mat != null) {
                        material.set(mat)
                    }
                    collisionGroup = customBone?.boneOverride?.collisionGroup ?: skel!!.all.collisionGroup
                }
            }
        }
        val boneLength = bone.data.length
        if (boneLength > 0) {
            return FixtureSpec {
                shape = customBone?.boneOverride?.shape ?: Rectangle(boneLength, skel!!.boneWidth)
                offsetX = customBone?.boneOverride?.offsetX ?: boneLength / 2
                offsetY = customBone?.boneOverride?.offsetX ?: 0f
                density = customBone?.boneOverride?.mass ?: mass
                restitution = customBone?.boneOverride?.restitution ?: skel!!.all.restitution
                friction = customBone?.boneOverride?.friction ?: skel!!.all.friction
                val mat = customBone?.boneOverride?.material ?: skel!!.all.material
                if (mat != null) {
                    material.set(mat)
                }
                collisionGroup = customBone?.boneOverride?.collisionGroup ?: skel!!.all.collisionGroup
            }
        }
        return FixtureSpec {
            shape = Circle(skel!!.boneWidth)
            density = mass
            collisionGroup = skel!!.all.collisionGroup
        }
    }

    private fun findPolygon(bone: Bone, skeleton: Skeleton): Polygon? {
        val boneName = bone.data.name
        for (slot in skeleton.slots) {
            if (slot.bone.data.name == boneName) {
                if (slot.attachment is BoundingBoxAttachment) {
                    val bb: BoundingBoxAttachment = slot.attachment as BoundingBoxAttachment
                    val poly = Polygon()
                    for (i in 0..bb.vertices.size - 2 step 2) {
                        poly.vertices.add(vec2(bb.vertices[i], bb.vertices[i + 1]))
                    }
                    return poly
                }
            }
        }
        return null
    }
}

class RenderModel {
    var atlasPath: String? = null
    var dataPath: String? = null
    var originX: Float = 0f
    var originY: Float = 0f
    var scale: Float = 1f
    var shader: ShaderSpec? = null
    var layer = 0
    var priority = 0
}

internal class CrossFade(
        val fromName: String,
        val toName: String,
        val duration: Float
)

internal class EventToComponentCall(
        val componentType: KClass<Component>,
        val componentFunction: KFunction2<Component, @ParameterName(name = "event") Event, Unit>
)

internal class ComponentCall(
        val componentType: KClass<Component>,
        val componentFunction: KFunction1<Component, Unit>
)

class SpineAnimations {
    var startingAnimation: String? = null
    internal val crossFades: Array<CrossFade> = Array()
    internal var functionHandlers: ObjectMap<String, SpineEventHandler> = ObjectMap()
    internal var componentHandlers: ObjectMap<String, EventToComponentCall> = ObjectMap()
    internal var componentFunctions: ObjectMap<String, ComponentCall> = ObjectMap()

    fun setMix(fromName: String, toName: String, duration: Float) {
        crossFades.add(CrossFade(fromName, toName, duration))
    }

    fun registerEventHandler(eventName: String, handler: SpineEventHandler) {
        functionHandlers.put(eventName, handler)
    }

    fun <T : Component> registerEventHandler(eventName: String, componentType: KClass<T>, componentFunction: KFunction2<T, @ParameterName(name = "event") Event, Unit>) {
        componentHandlers.put(eventName, EventToComponentCall(componentType as KClass<Component>, componentFunction as KFunction2<Component, @kotlin.ParameterName(name = "event") Event, Unit>))
    }

    fun <T : Component> registerEventFunction(eventName: String, componentType: KClass<T>, componentFunction: KFunction1<T, Unit>) {
        componentFunctions.put(eventName, ComponentCall(componentType as KClass<Component>, componentFunction as KFunction1<Component, Unit>))
    }
}







