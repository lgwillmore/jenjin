package com.binarymonks.jj.core.specs

import com.badlogic.gdx.physics.box2d.JointDef

interface SceneSpecRef {
    fun resolve(): SceneSpec
}

class SceneSpecRefPath(val path: String) : SceneSpecRef {
    override fun resolve(): SceneSpec {
        TODO("not implemented") //To change physics of created functions use File | Settings | File Templates.
    }
}

class SceneSpecRefProxy(val sceneSpec: SceneSpec) : SceneSpecRef {
    override fun resolve(): SceneSpec {
        return sceneSpec
    }
}

open class SceneSpec {

    var thingSpec :ThingSpec? = null

    /**
     * Add a addNode [SceneSpec] instance
     *
     * @param scene The scene to instantiate
     * @param instanceParams The instance specific parameters
     */
    fun addNode(scene: SceneSpec, instanceParams: InstanceParams = InstanceParams.new()) {

    }

    /**
     * Add a addNode [SceneSpec] instance
     *
     * @param scenePath The path to the addNode[SceneSpec].
     * @param instanceParams The instance specific parameters
     */
    fun addNode(scenePath: String, instanceParams: InstanceParams = InstanceParams.new()) {

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
    fun joint(thingAName: String, thingBname: String, jointDef : JointDef) {

    }

}