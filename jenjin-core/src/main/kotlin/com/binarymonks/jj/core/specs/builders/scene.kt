package com.binarymonks.jj.core.specs.builders

import com.badlogic.gdx.math.Vector2
import com.binarymonks.jj.core.audio.SoundParams
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.specs.InstanceParams
import com.binarymonks.jj.core.specs.SceneSpec
import com.binarymonks.jj.core.specs.physics.PhysicsSpec
import com.binarymonks.jj.core.specs.physics.RevoluteJointSpec
import com.binarymonks.jj.core.specs.physics.WeldJointSpec
import com.binarymonks.jj.core.specs.render.RenderSpec

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

fun SceneSpec.physics(init: PhysicsSpec.() -> Unit): PhysicsSpec {
    this.physics.init()
    return this.physics
}

fun SceneSpec.render(init: RenderSpec.() -> Unit) {
    this.render.init()
}

fun SceneSpec.sound(name: String, path: String, init: SoundParams.() -> Unit): SoundParams {
    val soundParams = SoundParams(name)
    soundParams.soundPaths.add(path)
    soundParams.init()
    this.sounds.add(soundParams)
    return soundParams
}

fun <T : Component> SceneSpec.component(component: T, init: T.() -> Unit): T {
    component.init()
    this.components.add(component)
    return component
}

fun SceneSpec.revJoint(nameA: String?, nameB: String, anchor: Vector2, init: RevoluteJointSpec.() -> Unit) {
    val revJoint = RevoluteJointSpec(nameA, nameB, anchor)
    revJoint.init()
    this.joints.add(revJoint)
}

fun SceneSpec.revJoint(nameA: String?, nameB: String, localAnchorA: Vector2, localAnchorB: Vector2,init: RevoluteJointSpec.() -> Unit) {
    val revJoint = RevoluteJointSpec(nameA, nameB, localAnchorA,localAnchorB)
    revJoint.init()
    this.joints.add(revJoint)
}

fun SceneSpec.weldJoint(nameA: String, nameB: String, anchor: Vector2, init: WeldJointSpec.() -> Unit) {
    val revJoint = WeldJointSpec(nameA, nameB, anchor)
    revJoint.init()
    this.joints.add(revJoint)
}

