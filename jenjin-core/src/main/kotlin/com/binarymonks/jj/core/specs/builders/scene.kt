package com.binarymonks.jj.core.specs.builders

import com.binarymonks.jj.core.specs.InstanceParams
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.ThingSpec

/**
 * This provides builders for [com.binarymonks.jj.core.api.specs.SceneSpec]s
 */

/**
 * A [InstanceParams] builder
 */
fun params(init: InstanceParams.() -> Unit): InstanceParams {
    val instanceParams = InstanceParams.new()
    instanceParams.init()
    return instanceParams
}

/**
 * A [SceneSpec] builder
 */
fun scene(init: SceneSpec.() -> Unit): SceneSpec {
    val basicScene = SceneSpec()
    basicScene.init()
    return basicScene
}

fun SceneSpec.node(instanceParams: InstanceParams = InstanceParams.new(), init: SceneSpec.() -> Unit): SceneSpec {
    val sceneSpec = SceneSpec()
    sceneSpec.init()
    this.addNode(sceneSpec, instanceParams)
    return sceneSpec
}

fun SceneSpec.nodeRef(instanceParams: InstanceParams = InstanceParams.new(), pathProvider: () -> String) {
    this.addNode(pathProvider.invoke(), instanceParams)
}

fun SceneSpec.thing(init: ThingSpec.() -> Unit): ThingSpec {
    val thing = ThingSpec()
    thing.init()
    this.thingSpec = thing
    return thing
}

