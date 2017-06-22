package com.binarymonks.jj.core.specs.physics

import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.JointDef
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.binarymonks.jj.core.extensions.copy

/**
 * Like a [com.badlogic.gdx.physics.box2d.JointDef] but with names of Things in a scene rather than bodies.
 *
 * @param nameA instance name of Body A. Leave null to use scene Body.
 * @param nameB instance name of Body B.
 */
abstract class JointSpec(
        val nameA: String?,
        val nameB: String
) {
    var collideConnected: Boolean = false

    abstract fun toJointDef(bodyA: Body, bodyB: Body, transform: Matrix3): JointDef
}

class RevoluteJointSpec(
        nameA: String?,
        nameB: String
) : JointSpec(nameA, nameB) {

    constructor(nameA: String?,
                nameB: String,
                anchor: Vector2) : this(nameA, nameB) {
        this.anchor = anchor
    }

    constructor(nameA: String?,
                nameB: String,
                localAnchorA: Vector2,
                localAnchorB: Vector2
    ) : this(nameA, nameB) {
        this.localAnchorB = localAnchorB
        this.localAnchorA = localAnchorA
    }

    var anchor: Vector2? = null
        private set
    var localAnchorA: Vector2? = null
        private set
    var localAnchorB: Vector2? = null
        private set
    var enableLimit = false
    var lowerAngle = 0f
    var upperAngle = 0f
    var enableMotor = false
    var motorSpeed = 0f
    var maxMotorTorque = 0f

    override fun toJointDef(bodyA: Body, bodyB: Body, transform: Matrix3): JointDef {
        val revJoint = RevoluteJointDef()
        if (anchor != null) {
            revJoint.initialize(bodyA, bodyB, anchor!!.copy().mul(transform))
        } else {
            revJoint.bodyA=bodyA
            revJoint.bodyB=bodyB
            revJoint.localAnchorA.set(localAnchorA!!.copy())
            revJoint.localAnchorB.set(localAnchorB!!.copy())
        }
        revJoint.enableLimit = enableLimit
        revJoint.lowerAngle = lowerAngle
        revJoint.upperAngle = upperAngle
        revJoint.enableMotor = enableMotor
        revJoint.motorSpeed = motorSpeed
        revJoint.maxMotorTorque = maxMotorTorque
        return revJoint
    }

}

class WeldJointSpec(
        nameA: String,
        nameB: String,
        val anchor: Vector2
) : JointSpec(nameA, nameB) {

    var frequencyHz = 0f
    var dampingRatio = 0f

    override fun toJointDef(bodyA: Body, bodyB: Body, transform: Matrix3): JointDef {
        val weldJoint = WeldJointDef()
        weldJoint.initialize(bodyA, bodyB, anchor.copy().mul(transform))
        weldJoint.frequencyHz = frequencyHz
        weldJoint.dampingRatio = dampingRatio
        return weldJoint
    }
}
