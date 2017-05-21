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

    var thingSpec :ThingSpecI = ThingSpecNull()

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

}