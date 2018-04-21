package com.binarymonks.jj.core.components.fsm

import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.Copyable
import com.binarymonks.jj.core.copy
import com.binarymonks.jj.core.scenes.Scene


abstract class TransitionCondition : Copyable<TransitionCondition> {

    open internal var scene: Scene? = null
    open internal var machine: StateMachine? = null

    fun me(): Scene {
        return scene!!
    }

    fun myMachine(): StateMachine{
        if(machine == null){
            throw Exception("I cannot see my machine yet. I can only see it after I have been activated")
        }
        return machine!!
    }

    abstract fun met(): Boolean

    override fun clone(): TransitionCondition {
        return copy(this)
    }

}


class AndTransitionCondition() : TransitionCondition() {

    var conditions: Array<TransitionCondition> = Array()

    override var scene: Scene?
        get() = super.scene
        set(value) {
            super.scene = value
            conditions.forEach { it.scene = value }
        }

    override var machine: StateMachine?
        get() = super.machine
        set(value) {
            super.machine = value
            conditions.forEach { it.machine = value }
        }

    constructor(construc: AndTransitionCondition.() -> Unit) : this() {
        this.construc()
    }

    fun <T : TransitionCondition> and(condition: T) {
        conditions.add(condition)
    }

    override fun met(): Boolean {
        if (conditions.size == 0) {
            return false
        }
        conditions.forEach {
            if (it.met() == false) {
                return false
            }
        }
        return true
    }

}

class OrTransitionCondition() : TransitionCondition() {

    var conditions: Array<TransitionCondition> = Array()

    override var scene: Scene?
        get() = super.scene
        set(value) {
            super.scene = value
            conditions.forEach { it.scene = value }
        }

    override var machine: StateMachine?
        get() = super.machine
        set(value) {
            super.machine = value
            conditions.forEach { it.machine = value }
        }

    constructor(construc: OrTransitionCondition.() -> Unit) : this() {
        this.construc()
    }

    fun <T : TransitionCondition> or(condition: T) {
        conditions.add(condition)
    }

    override fun met(): Boolean {
        if (conditions.size == 0) {
            return false
        }
        conditions.forEach {
            if (it.met() == true) {
                return true
            }
        }
        return false
    }

}

class NotTransitionCondition() : TransitionCondition() {

    var condition: TransitionCondition? = null

    override var scene: Scene?
        get() = super.scene
        set(value) {
            super.scene = value
            condition?.scene=value
        }

    constructor(condition: TransitionCondition) : this() {
        this.condition = condition
    }

    override fun met(): Boolean {
        if (condition == null) {
            return false
        }
        return !condition!!.met()
    }
}

fun not(condition: TransitionCondition): TransitionCondition {
    return NotTransitionCondition(condition)
}