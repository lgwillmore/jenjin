package com.binarymonks.jj.core.components.fsm

import com.badlogic.gdx.utils.ObjectMap
import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.components.Component
import com.binarymonks.jj.core.copy
import com.binarymonks.jj.core.scenes.Scene


open class StateMachine() : State() {

    var initialState: String? = null
    val states: ObjectMap<String, State> = ObjectMap()
    val transitions: ObjectMap<String, Array<TransitionEdge>> = ObjectMap()
    private var currentState: String? = null
    private var requestedTransition: String? = null

    override var scene: Scene?
        get() = super.scene
        set(value) {
            super.scene = value
            states.forEach {
                it.value.scene = value
            }
            transitions.forEach {
                it.value.forEach {
                    it.condition?.scene = value
                }
            }
        }

    constructor(construct: StateMachineBuilder.() -> Unit) : this() {
        val builder = StateMachineBuilder(this)
        builder.construct()
    }

    fun buildStates(construct: StateMachineBuilder.() -> Unit) {
        val builder = StateMachineBuilder(this)
        builder.construct()
    }

    override fun onAddToScene() {
        states.forEach { it.value.onAddToScene() }
    }

    override fun onAddToWorld() {
        states.forEach { it.value.onAddToWorld() }
    }

    override fun onFullyInitialized() {
        states.forEach { it.value.onFullyInitialized() }
    }

    override fun onRemoveFromScene() {
        states.forEach { it.value.onRemoveFromScene() }
    }

    override fun onRemoveFromWorld() {
        currentState=null
        states.forEach { it.value.onRemoveFromWorld() }
    }

    override fun update() {
        if (requestedTransition != null) {
            if(currentState!=null){
                state().exitWrapper()
            }
            currentState = requestedTransition
            requestedTransition = null
            state().enterWrapper()
        }
        if (currentState == null) {
            if(initialState==null){
                throw Exception("You need to set an initial state")
            }
            currentState = initialState
            state().enterWrapper()
        }
        for (edge in transitions.get(currentState)) {
            if (edge.condition!!.met()) {
                state().exitWrapper()
                currentState = edge.toEventName
                state().enterWrapper()
                break
            }
        }
        state().update()
    }

    fun state(): State {
        return states.get(currentState)
    }



    override fun clone(): Component {
        val myClone = super.clone() as StateMachine
        myClone.states.clear()
        states.forEach {
            myClone.states.put(it.key, it.value.clone() as State)
        }
        myClone.transitions.clear()
        transitions.forEach {
            val edges: Array<TransitionEdge> = Array()
            it.value.forEach { edges.add(it.clone()) }
            myClone.transitions.put(it.key, edges)
        }
        return myClone
    }

    fun transitionTo(stateName: String) {
        requestedTransition = stateName
    }
}

class TransitionEdge() {
    var condition: TransitionCondition? = null
    var toEventName: String? = null

    constructor(
            condition: TransitionCondition,
            toEventName: String
    ) : this() {
        this.condition = condition
        this.toEventName = toEventName
    }

    fun clone(): TransitionEdge {
        return copy(this)
    }
}


class StateMachineBuilder(
        val stateMachine: StateMachine
) {


    fun <T : State> initialState(name: String, component: T): TransitionBuilder {
        stateMachine.initialState = name
        stateMachine.states.put(name, component)
        stateMachine.transitions.put(name, Array<TransitionEdge>())
        return TransitionBuilder(name, stateMachine)
    }

    fun <T : State> addState(name: String, component: T): TransitionBuilder {
        stateMachine.states.put(name, component)
        stateMachine.transitions.put(name, Array<TransitionEdge>())
        return TransitionBuilder(name, stateMachine)
    }

    fun addComposite(name: String, build: CompositeState.() -> Unit): TransitionBuilder {
        val composite = CompositeState()
        composite.build()
        stateMachine.transitions.put(name, Array<TransitionEdge>())
        stateMachine.states.put(name, composite)
        return TransitionBuilder(name, stateMachine)
    }

}

class TransitionBuilder(
        val from: String,
        val stateMachine: StateMachine
) {

    fun withTransitions(build: TransitionBuilder.() -> Unit) {
        this.build()
    }

    fun to(stateName: String): TransitionBuilderTo {

        return TransitionBuilderTo(from, stateName, stateMachine)
    }
}

class TransitionBuilderTo(
        val from: String,
        val to: String,
        val stateMachine: StateMachine
) {

    fun <T : TransitionCondition> whenJust(whenCondition: T) {
        stateMachine.transitions.get(from).add(TransitionEdge(whenCondition, to))
    }

    fun <T : TransitionCondition> whenNot(whenCondition: T) {
        stateMachine.transitions.get(from).add(TransitionEdge(not(whenCondition), to))
    }

    fun whenAll(build: AndTransitionCondition.() -> Unit) {
        val and = AndTransitionCondition()
        and.build()
        stateMachine.transitions.get(from).add(TransitionEdge(and, to))
    }

    fun whenAny(build: OrTransitionCondition.() -> Unit) {
        val or = OrTransitionCondition()
        or.build()
        stateMachine.transitions.get(from).add(TransitionEdge(or, to))
    }


}