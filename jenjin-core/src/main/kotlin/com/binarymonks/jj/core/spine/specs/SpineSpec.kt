package com.binarymonks.jj.core.spine.specs

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.BodyDef
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.assets.AssetReference
import com.binarymonks.jj.core.extensions.copy
import com.binarymonks.jj.core.pools.vec2
import com.binarymonks.jj.core.specs.Circle
import com.binarymonks.jj.core.specs.Rectangle
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.SceneSpecRef
import com.binarymonks.jj.core.specs.builders.*
import com.binarymonks.jj.core.specs.physics.FixtureSpec
import com.binarymonks.jj.core.spine.collisions.TriggerRagDollCollision
import com.binarymonks.jj.core.spine.components.SpineBoneComponent
import com.esotericsoftware.spine.Bone
import com.esotericsoftware.spine.Skeleton
import com.esotericsoftware.spine.SkeletonJson
import com.esotericsoftware.spine.attachments.AtlasAttachmentLoader


class SpineSpec() : SceneSpecRef {

    var atlasPath: String? = null
    var dataPath: String? = null
    var scale: Float = 1f
    var originX: Float = 0f
    var originY: Float = 0f
    var startingAnimation: String? = null
    var spineSkeleton: SpineSkeletonSpec? = null


    constructor(build: com.binarymonks.jj.core.spine.specs.SpineSpec.() -> Unit) : this() {
        this.build()
    }

    override fun resolve(): SceneSpec {
        val scene = scene {
            physics {
                bodyType = BodyDef.BodyType.KinematicBody
            }
            render {
                renderNodes.add(
                        SpineRenderNodeSpec(
                                atlasPath,
                                dataPath,
                                originX,
                                originY,
                                scale
                        )
                )
            }
            component(com.binarymonks.jj.core.spine.components.SpineComponent()) {
                startingAnimation = this@SpineSpec.startingAnimation
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
            buildPhysicsSkeleton(scene, skeleton)
        }
        return scene
    }

    override fun getAssets(): Array<AssetReference> {
        val assets: Array<AssetReference> = Array()
        assets.add(AssetReference(TextureAtlas::class, checkNotNull(atlasPath)))
        return assets
    }

    private fun buildPhysicsSkeleton(scene: SceneSpec, skeleton: Skeleton) {
        val bone = skeleton.rootBone
        val path: Array<String> = Array()
        path.add(bone.data.name)
        buildBoneRecurse(bone, spineSkeleton!!.coreMass, path, scene)
    }

    private fun buildBoneRecurse(bone: Bone, mass: Float, path: Array<String>, parentScene: SceneSpec): String {
        parentScene.addNode(
                scene {
                    physics {
                        bodyType = BodyDef.BodyType.DynamicBody
                        gravityScale = 0f
                        val fixture = buildFixture(bone, mass)
                        addFixture(fixture)
                        spineSkeleton!!.beginCollisions.forEach { beginCollision(it) }
                        spineSkeleton!!.finalBeginCollisions.forEach { finalBeginCollision(it) }
                        spineSkeleton!!.endCollisions.forEach { endCollision(it) }
                    }
                    component(SpineBoneComponent()) {
                        bonePath = path
                    }
                    bone.children.forEach {
                        val a = path.copy()
                        a.add(it.data.name)
                        val childName = buildBoneRecurse(it, mass * spineSkeleton!!.massFalloff, a, this@scene)
                        revJoint(null, childName, vec2(it.x, it.y), vec2()) {
                            enableLimit = true
                            lowerAngle = spineSkeleton!!.jointLowerLimitD * MathUtils.degRad
                            upperAngle = spineSkeleton!!.jointUpperLimitD * MathUtils.degRad
                            collideConnected = false
                        }
                    }
                },
                params { name = bone.data.name }
        )
        return bone.data.name
    }

    private fun buildFixture(bone: Bone, mass: Float): FixtureSpec {
        if (spineSkeleton!!.boneFixtureOverrides.containsKey(bone.data.name)) {
            return spineSkeleton!!.boneFixtureOverrides[bone.data.name]
        }
        val boneLength = bone.data.length
        if (boneLength > 0) {
            return FixtureSpec {
                shape = Rectangle(boneLength, spineSkeleton!!.boneWidth)
                offsetX = boneLength / 2
                density = mass
                restitution = spineSkeleton!!.restitution
                friction = spineSkeleton!!.friction
                collisionGroup = spineSkeleton!!.collisionGroup
            }
        }
        return FixtureSpec {
            shape = Circle(spineSkeleton!!.boneWidth)
            density = mass
            collisionGroup = spineSkeleton!!.collisionGroup
        }
    }
}

