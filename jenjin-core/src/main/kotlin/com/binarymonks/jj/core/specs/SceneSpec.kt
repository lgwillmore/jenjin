package com.binarymonks.jj.core.specs

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.Copyable
import com.binarymonks.jj.core.JJ
import com.binarymonks.jj.core.specs.physics.JointSpec

internal var sceneIDCounter = 0

open class SceneSpec {
    var id = sceneIDCounter++
    internal var nodeCounter = 0
    var thingSpec: ThingSpec? = null
    var nodes: ObjectMap<String, SceneNode> = ObjectMap()
    var joints: Array<JointSpec> = Array()


    /**
     * Add a addNode [SceneSpec] instance
     *
     * @param scene The scene to instantiate
     * @param instanceParams The instance specific parameters
     */
    fun addNode(scene: SceneSpec, instanceParams: InstanceParams = InstanceParams.new()) {
        nodes.put(getName(instanceParams), SceneNode(SceneSpecRefProxy(scene), instanceParams))
    }

    /**
     * Add a addNode [SceneSpec] instance
     *
     * @param scenePath The path to the addNode[SceneSpec].
     * @param instanceParams The instance specific parameters
     */
    fun addNode(scenePath: String, instanceParams: InstanceParams = InstanceParams.new()) {
        nodes.put(getName(instanceParams), SceneNode(SceneSpecRefPath(scenePath), instanceParams))
    }

    private fun getName(instanceParams: InstanceParams): String? {
        return if (instanceParams.name == null) {
            "ANON${nodeCounter++}"
        } else {
            instanceParams.name
        }
    }

}

class SceneNode(
        val sceneRef: SceneSpecRef? = null,
        val instanceParams: InstanceParams = InstanceParams.new()
)

interface SceneSpecRef : Copyable<SceneSpecRef> {
    fun resolve(): SceneSpec
}

fun sceneRef(path: String): SceneSpecRef {
    return SceneSpecRefPath(path)
}

fun sceneRef(scene: SceneSpec): SceneSpecRef {
    return SceneSpecRefProxy(scene)
}

class SceneSpecRefPath(val path: String) : SceneSpecRef {
    override fun copy(): SceneSpecRef {
        return SceneSpecRefPath(path)
    }

    override fun resolve(): SceneSpec {
        return JJ.B.scenes.getScene(path)
    }
}

class SceneSpecRefProxy(val sceneSpec: SceneSpec) : SceneSpecRef {
    override fun copy(): SceneSpecRef {
        return SceneSpecRefProxy(sceneSpec)
    }

    override fun resolve(): SceneSpec {
        return sceneSpec
    }
}