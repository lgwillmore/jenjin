package com.binarymonks.jj.core.spine.specs

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.utils.Array
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
import com.binarymonks.jj.core.spine.components.SpineBoneComponent
import com.binarymonks.jj.core.spine.components.SpineComponent
import com.esotericsoftware.spine.Bone
import com.esotericsoftware.spine.Skeleton
import com.esotericsoftware.spine.SkeletonJson
import com.esotericsoftware.spine.attachments.AtlasAttachmentLoader
import com.esotericsoftware.spine.attachments.BoundingBoxAttachment


class SpineSpec() : SceneSpecRef {

    var atlasPath: String? = null
    var dataPath: String? = null
    var scale: Float = 1f
    var originX: Float = 0f
    var originY: Float = 0f
    var layer = 0
    var shaderSpec: ShaderSpec? = null
    var startingAnimation: String? = null
    var spineSkeleton: SpineSkeletonSpec? = null
    var rootComponents: Array<Component> = Array()


    constructor(build: com.binarymonks.jj.core.spine.specs.SpineSpec.() -> Unit) : this() {
        this.build()
    }

    override fun resolve(): SceneSpec {
        val spineComponent = SpineComponent()
        val scene = scene {
            physics {
                bodyType = BodyDef.BodyType.KinematicBody
            }
            render {
                val renderSpec = SpineRenderNodeSpec(
                        atlasPath,
                        dataPath,
                        originX,
                        originY,
                        scale,
                        shaderSpec
                )
                renderSpec.layer = layer
                renderNodes.add(renderSpec)
            }
            component(spineComponent) {
                startingAnimation = this@SpineSpec.startingAnimation
            }
            for (component in rootComponents) {
                this.component(component)
            }
        }
        if (spineSkeleton != null) {
            val atlas = JJ.assets.getAsset(checkNotNull(atlasPath), TextureAtlas::class)
            val atlasLoader = AtlasAttachmentLoader(atlas)
            val json = SkeletonJson(atlasLoader)
            val actualScale = scale
            json.scale = actualScale
            val skeletonData = json.readSkeletonData(Gdx.files.internal(dataPath))
            val skeleton = Skeleton(skeletonData)
            buildPhysicsSkeleton(scene, skeleton, spineComponent)
        }
        return scene
    }

    override fun getAssets(): Array<AssetReference> {
        val assets: Array<AssetReference> = Array()
        assets.add(AssetReference(TextureAtlas::class, checkNotNull(atlasPath)))
        return assets
    }

    private fun buildPhysicsSkeleton(scene: SceneSpec, skeleton: Skeleton, spineComponent: SpineComponent) {
        val bone = skeleton.rootBone
        val path: Array<String> = Array()
        path.add(bone.data.name)
        buildBoneRecurse(bone, spineSkeleton!!.coreMass, path, scene, spineComponent, skeleton)
    }

    private fun buildBoneRecurse(bone: Bone, mass: Float, path: Array<String>, parentScene: SceneSpec, spineComponent: SpineComponent, skeleton: Skeleton): String {
        parentScene.addNode(
                scene {
                    physics {
                        bodyType = BodyDef.BodyType.DynamicBody
                        gravityScale = 0f
                        val fixture = buildFixture(bone, mass, skeleton)
                        addFixture(fixture)
                        collisions.copyAppendFrom(spineSkeleton!!.all.collisions)
                    }
                    component(SpineBoneComponent()) {
                        bonePath = path
                    }
                    bone.children.forEach {
                        val a = path.copy()
                        a.add(it.data.name)
                        val childName = buildBoneRecurse(it, mass * spineSkeleton!!.massFalloff, a, this@scene, spineComponent, skeleton)
                        revJoint(null, childName, vec2(it.x, it.y), vec2()) {
                            collideConnected = false
                            enableMotor=true
                            motorSpeed=0f
                            maxMotorTorque=0.5f
                        }
                    }
                    spineSkeleton!!.all.properties.forEach {
                        prop(it.key, it.value)
                    }
                    for (component in spineSkeleton!!.all.components) {
                        this.component(component)
                    }
                },
                params { name = bone.data.name }
        )
        spineComponent.bonePaths.put(bone.data.name, ScenePath(path.copy()))
        return bone.data.name
    }

    private fun buildFixture(bone: Bone, mass: Float, skeleton: Skeleton): FixtureSpec {
        if (spineSkeleton!!.boneFixtureOverrides.containsKey(bone.data.name)) {
            return spineSkeleton!!.boneFixtureOverrides[bone.data.name]
        }
        if (spineSkeleton!!.boundingBoxes) {
            val boundingBox: Polygon? = findPolygon(bone, skeleton)
            if (boundingBox != null) {
                return FixtureSpec {
                    shape = boundingBox
                    density = mass
                    restitution = spineSkeleton!!.all.restitution
                    friction = spineSkeleton!!.all.friction
                    val mat = spineSkeleton!!.all.material
                    if (mat != null) {
                        material.set(mat)
                    }
                    collisionGroup = spineSkeleton!!.all.collisionGroup
                }
            }
        }
        val boneLength = bone.data.length
        if (boneLength > 0) {
            return FixtureSpec {
                shape = Rectangle(boneLength, spineSkeleton!!.boneWidth)
                offsetX = boneLength / 2
                density = mass
                restitution = spineSkeleton!!.all.restitution
                friction = spineSkeleton!!.all.friction
                collisionGroup = spineSkeleton!!.all.collisionGroup
                val mat = spineSkeleton!!.all.material
                if (mat != null) {
                    material.set(mat)
                }
            }
        }
        return FixtureSpec {
            shape = Circle(spineSkeleton!!.boneWidth)
            density = mass
            collisionGroup = spineSkeleton!!.all.collisionGroup
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

