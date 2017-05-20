package com.binarymonks.jj.core.api.specs.builders
import com.binarymonks.jj.core.api.specs.BasicScene
import com.binarymonks.jj.core.api.specs.InstanceParams
import com.binarymonks.jj.core.api.specs.SceneSpec
/**
 * This provides builders for [com.binarymonks.jj.core.api.specs.SceneSpec]s
 *
 * Yes - it builds a DSL for building scenes. If you don't like it, use the raw objects and ignore this :P
 */


/**
 * A [BasicScene] builder
 */
fun basicScene(init: BasicScene.() -> Unit): BasicScene {
    val basicScene = BasicScene()
    basicScene.init()
    return basicScene
}

/**
 * Add a Scene as a node/child to the Scene
 */
fun SceneSpec.node(instanceParams: InstanceParams = InstanceParams.new(), childInit: () -> SceneSpec) {
    val child = childInit()
    this.addChild(child, instanceParams)
}


/**
 * Add a scene path as a node/child to the scene
 */
fun SceneSpec.node(instanceParams: InstanceParams = InstanceParams.new(), scenePath: String) {
    this.addChild(scenePath, instanceParams)
}




