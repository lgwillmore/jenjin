package com.binarymonks.jj.core.specs

import com.badlogic.gdx.physics.box2d.JointDef
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ

interface SceneSpecRef {
    val params: InstanceParams
    fun resolve(): SceneSpec
}

class SceneSpecRefPath(val path: String, override val params: InstanceParams) : SceneSpecRef {
    override fun resolve(): SceneSpec {
        return JJ.B.scenes.getScene(path)
    }
}

class SceneSpecRefProxy(val sceneSpec: SceneSpec, override val params: InstanceParams) : SceneSpecRef {
    override fun resolve(): SceneSpec {
        return sceneSpec
    }
}

internal var sceneIDCounter = 0

open class SceneSpec {
    var id = sceneIDCounter++
    internal var nodeCounter = 0
    var thingSpec: ThingSpec? = null
    var nodes: ObjectMap<String, SceneSpecRef> = ObjectMap()


    /**
     * Add a addNode [SceneSpec] instance
     *
     * @param scene The scene to instantiate
     * @param instanceParams The instance specific parameters
     */
    fun addNode(scene: SceneSpec, instanceParams: InstanceParams = InstanceParams.new()) {
        nodes.put(getName(instanceParams), SceneSpecRefProxy(scene, instanceParams))
    }

    /**
     * Add a addNode [SceneSpec] instance
     *
     * @param scenePath The path to the addNode[SceneSpec].
     * @param instanceParams The instance specific parameters
     */
    fun addNode(scenePath: String, instanceParams: InstanceParams = InstanceParams.new()) {
        nodes.put(getName(instanceParams), SceneSpecRefPath(scenePath, instanceParams))
    }

    private fun getName(instanceParams: InstanceParams): String? {
        return if (instanceParams.name == null) {
            "ANON${nodeCounter++}"
        } else {
            instanceParams.name
        }
    }

    /**
     * Add a joint between 2 nodes
     *
     *  - The nodes must have their [ThingSpecI] set to a real [ThingSpec]
     *  - The nodes [InstanceParams.name] must be set to something unique to the children of this scene.
     *
     *  @param thingAName The [InstanceParams.name] of node A
     *  @param thingBName The [InstanceParams.name] of node B
     */
    fun joint(thingAName: String, thingBname: String, jointDef: JointDef) {

    }

}