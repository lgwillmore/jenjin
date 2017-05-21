package com.binarymonks.jj.core.api.specs.builders
import com.binarymonks.jj.core.api.specs.InstanceParams
import com.binarymonks.jj.core.api.specs.SceneSpec
/**
 * This provides builders for [com.binarymonks.jj.core.api.specs.SceneSpec]s
 *
 * Yes - it builds a DSL for building scenes. If you don't like it, use the raw objects and ignore this :P
 */

/**
 * A [SceneSpec] builder
 */
fun scene(init: SceneSpec.() -> Unit): SceneSpec {
    val basicScene = SceneSpec()
    basicScene.init()
    return basicScene
}




