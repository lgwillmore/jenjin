package com.binarymonks.jj.core.api.specs

interface SceneSpecRef {
    fun resolve(): SceneSpec
}

class SceneSpecRefPath(val path: String) : SceneSpecRef {
    override fun resolve(): SceneSpec {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class SceneSpecRefProxy(val sceneSpec: SceneSpec) : SceneSpecRef {
    override fun resolve(): SceneSpec {
        return sceneSpec
    }
}

open class SceneSpec {

    var thingSpec: ThingSpec =


    /**
     * Add a node [SceneSpec] instance
     *
     * @param scene The scene to instantiate
     * @param instanceParams The instance specific parameters
     */
    fun node(scene: SceneSpec, instanceParams: InstanceParams = InstanceParams.new()) {

    }

    /**
     * Add a node [SceneSpec] instance
     *
     * @param scenePath The path to the node[SceneSpec].
     * @param instanceParams The instance specific parameters
     */
    fun node(scenePath: String, instanceParams: InstanceParams = InstanceParams.new()) {

    }

}