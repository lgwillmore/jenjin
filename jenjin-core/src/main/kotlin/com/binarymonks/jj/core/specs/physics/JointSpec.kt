package com.binarymonks.jj.core.specs.physics

import com.badlogic.gdx.physics.box2d.JointDef

/**
 * Like a [com.badlogic.gdx.physics.box2d.JointDef] but with names of Things in a scene rather than bodies.
 */
abstract class JointSpec(
        val nameA: String,
        val nameB: String
) {
    val collideConnected: Boolean = false

    abstract fun toJointDef(): JointDef
}

class RevoluteJointSpec(
        nameA: String,
        nameB: String
) : JointSpec(nameA, nameB) {


    override fun toJointDef(): JointDef {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
