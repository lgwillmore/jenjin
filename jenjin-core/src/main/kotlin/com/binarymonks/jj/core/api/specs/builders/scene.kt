package com.binarymonks.jj.core.api.specs.builders
import com.binarymonks.jj.core.api.specs.B2DSceneSpec
import com.binarymonks.jj.core.api.specs.InstanceParams
import com.binarymonks.jj.core.api.specs.SceneSpec
import com.binarymonks.jj.core.api.specs.ThingSpec

/**
 * This provides builders for [com.binarymonks.jj.core.api.specs.SceneSpec]s
 */

/**
 * A [InstanceParams] builder
 */
fun params(init: InstanceParams.()->Unit):InstanceParams{
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

/**
 * A [ThingSpec] builder
 */
fun thing(init: ThingSpec.() ->Unit): ThingSpec{
    val thingSpec = ThingSpec()
    thingSpec.init()
    return thingSpec
}

/**
 * A [B2DSceneSpec] builder
 */
fun b2dscene(init: B2DSceneSpec.() -> Unit): B2DSceneSpec{
    val scene = B2DSceneSpec()
    scene.init()
    return scene
}




