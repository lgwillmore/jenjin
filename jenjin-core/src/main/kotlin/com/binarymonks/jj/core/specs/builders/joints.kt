package com.binarymonks.jj.core.specs.builders

import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef

/**
 * This provides builders for Box2D joint definitions
 */

/**
 * A [RevoluteJointDef] builder
 */
fun revolute(init: RevoluteJointDef.() -> Unit): RevoluteJointDef {
    val revJoint = RevoluteJointDef()
    revJoint.init()
    return revJoint
}


