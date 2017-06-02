package com.binarymonks.jj.core.specs.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.JointDef
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef

/**
 * Like a [com.badlogic.gdx.physics.box2d.JointDef] but with names of Things in a scene rather than bodies.
 */
abstract class JointSpec(
        val nameA: String,
        val nameB: String
) {
    val collideConnected: Boolean = false

    abstract fun toJointDef(bodyA: Body, bodyB: Body): JointDef
}

class RevoluteJointSpec(
        nameA: String,
        nameB: String,
        val anchor: Vector2
) : JointSpec(nameA, nameB) {

    var enableLimit = false
    var lowerAngle = 0f
    var upperAngle = 0f
    var enableMotor = false
    var motorSpeed = 0f
    var maxMotorTorque = 0f

    override fun toJointDef(bodyA: Body, bodyB: Body): JointDef {
        val revJoint = RevoluteJointDef()
        revJoint.initialize(bodyA, bodyB, anchor)
        revJoint.enableLimit = enableLimit
        revJoint.lowerAngle = lowerAngle
        revJoint.upperAngle = upperAngle
        revJoint.enableMotor = enableMotor
        revJoint.motorSpeed = motorSpeed
        revJoint.maxMotorTorque = maxMotorTorque
        return revJoint
    }

}
