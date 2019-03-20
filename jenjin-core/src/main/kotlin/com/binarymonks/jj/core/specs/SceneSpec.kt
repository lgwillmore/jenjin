package com.binarymonks.jj.core.specs

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.assets.AssetReference
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.specs.physics.*
import com.binarymonks.jj.core.specs.render.RenderSpec

internal var sceneIDCounter = 0

open class SceneSpec() : SceneSpecRef {

    var id = sceneIDCounter++
    var name: String? = null
    internal var nodeCounter = 0
    var physics: PhysicsSpec = PhysicsSpec()
    var render: RenderSpec = RenderSpec()
    var sounds = SoundSpec()
    var components: Array<Component> = Array()
    var properties: ObjectMap<String, Any> = ObjectMap()
    var pooled: Boolean = true
    var nodes: ObjectMap<String, SceneNode> = ObjectMap()
    var joints: Array<JointSpec> = Array()
    var layer = 0
    var container = false

    constructor(init: SceneSpec.() -> Unit) : this() {
        this.init()
    }


    /**
     * Add a addNode [SceneSpecRef] instance
     *
     * @param scene The scene to instantiate
     * @param instanceParams The instance specific parameters
     */
    fun addNode(scene: SceneSpecRef, instanceParams: Params = InstanceParams.new()) {
        val fullParams = InstanceParams.from(instanceParams)
        nodes.put(getName(fullParams), SceneNode(scene, fullParams))
    }

    /**
     * Add a addNode [SceneSpec] instance
     *
     * @param scenePath The path to the addNode[SceneSpec].
     * @param instanceParams The instance specific parameters
     */
    fun addNode(scenePath: String, instanceParams: Params = InstanceParams.new()) {
        val fullParams = InstanceParams.from(instanceParams)
        nodes.put(getName(fullParams), SceneNode(SceneSpecRefPath(scenePath), fullParams))
    }

    private fun getName(instanceParams: InstanceParams): String? {
        return if (instanceParams.localName == null) {
            "ANON${nodeCounter++}"
        } else {
            instanceParams.localName
        }
    }

    fun prop(key: String, value: Any? = null) {
        properties.put(key, value)
    }

    fun node(path: String, build: Params.() -> Unit) {
        val params = ParamsBase()
        params.build()
        this.addNode(path, params)
    }

    fun node(path: String) {
        val params = ParamsBase()
        this.addNode(path, params)
    }

    fun node(build: NodeBuilder.() -> Unit) {
        val builder = NodeBuilder()
        builder.build()
        this.addNode(builder, builder)
    }

    fun physics(init: PhysicsSpec.() -> Unit): PhysicsSpec {
        this.physics.init()
        return this.physics
    }

    fun render(init: RenderSpec.() -> Unit) {
        this.render.init()
    }


    fun <T : Component> component(component: T, init: T.() -> Unit) {
        component.init()
        this.components.add(component)
    }

    fun <T : Component> component(component: T) {
        this.components.add(component)
    }

    fun revJoint(nameA: String?, nameB: String, anchor: Vector2 = Vector2(), init: RevoluteJointSpec.() -> Unit) {
        val revJoint = RevoluteJointSpec(nameA, nameB, anchor)
        revJoint.init()
        this.joints.add(revJoint)
    }

    fun revJoint(nameA: String?, nameB: String, anchor: Vector2 = Vector2()) {
        val revJoint = RevoluteJointSpec(nameA, nameB, anchor)
        this.joints.add(revJoint)
    }

    fun revJoint(nameA: String?, nameB: String, localAnchorA: Vector2, localAnchorB: Vector2, init: RevoluteJointSpec.() -> Unit) {
        val revJoint = RevoluteJointSpec(nameA, nameB, localAnchorA, localAnchorB)
        revJoint.init()
        this.joints.add(revJoint)
    }

    fun prismaticJoint(nameA: String?, nameB: String, init: PrismaticJointSpec.() -> Unit) {
        val prisJoint = PrismaticJointSpec(nameA, nameB)
        prisJoint.init()
        this.joints.add(prisJoint)
    }

    fun weldJoint(nameA: String, nameB: String, anchor: Vector2, init: WeldJointSpec.() -> Unit) {
        val revJoint = WeldJointSpec(nameA, nameB, anchor)
        revJoint.init()
        this.joints.add(revJoint)
    }

    override fun resolve(): SceneSpec {
        return this
    }

    override fun getAssets(): Array<AssetReference> {
        val assets: Array<AssetReference> = Array()
        for (node in render.renderNodes) {
            assets.addAll(node.getAssets())
        }
        sounds.params.forEach {
            it.soundPaths.forEach {
                assets.add(AssetReference(Sound::class, it))
            }
        }
        nodes.forEach { assets.addAll(it.value.sceneRef!!.getAssets()) }
        return assets
    }

}

class NodeBuilder(private val params: Params = ParamsBase()) : SceneSpec(), Params by params {
    fun update(params: Params) {
        this.params.x = params.x
        this.params.y = params.y
        this.params.scaleX = params.scaleX
        this.params.scaleY = params.scaleY
        this.params.rotationD = params.rotationD
        this.params.localProperties = params.localProperties
        this.params.localName = params.localName
        this.params.groupName = params.groupName
    }
}


class SceneNode(
        val sceneRef: SceneSpecRef? = null,
        val instanceParams: InstanceParams = InstanceParams.new()
)

interface SceneSpecRef {
    fun resolve(): SceneSpec
    fun getAssets(): Array<AssetReference>
}

fun sceneRef(path: String): SceneSpecRef {
    return SceneSpecRefPath(path)
}

class SceneSpecRefPath(val path: String) : SceneSpecRef {

    override fun getAssets(): Array<AssetReference> {
        return Array()
    }

    override fun resolve(): SceneSpec {
        return JJ.B.scenes.getScene(path)
    }
}