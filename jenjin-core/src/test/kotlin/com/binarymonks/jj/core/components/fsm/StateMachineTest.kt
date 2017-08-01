package com.binarymonks.jj.core.components.fsm

import com.binarymonks.jj.core.scenes.Scene
import org.junit.Test
import org.mockito.Mockito


class StateMachineTest {

    @Test
    fun stateMachineInitializationAndLifecycle() {

        val scene = Mockito.mock(Scene::class.java)
        val initialStateMock = Mockito.mock(State::class.java)
        val anotherStateMock = Mockito.mock(State::class.java)
        val combinationStateMock = Mockito.mock(State::class.java)
        val mocks = listOf<State>(initialStateMock, anotherStateMock, combinationStateMock)

        val stateMachine = StateMachine {
            initialState("initial", initialStateMock)
            addState("another", anotherStateMock)
            addComposite("composite") {
                part(combinationStateMock)
            }
        }
        stateMachine.onAddToScene()
        mocks.forEach {
            Mockito.verify(it).onAddToScene()
        }
        stateMachine.onAddToWorld()
        mocks.forEach {
            Mockito.verify(it).onAddToWorld()
        }
        stateMachine.scene = scene
        mocks.forEach {
            Mockito.verify(it).scene = scene
        }
        stateMachine.onRemoveFromScene()
        mocks.forEach {
            Mockito.verify(it).onRemoveFromScene()
        }
        stateMachine.onRemoveFromWorld()
        mocks.forEach {
            Mockito.verify(it).onRemoveFromWorld()
        }
    }

    @Test
    fun transitionsStatesInitial() {
        val initialStateMock = Mockito.mock(State::class.java)

        val stateMachine = StateMachine {
            initialState("initial", initialStateMock)
        }

        stateMachine.update()
        Mockito.verify(initialStateMock).enter()
    }

    @Test
    fun transitionsStatesWhenConditionMet() {
        val initialStateMock = Mockito.mock(State::class.java)
        val anotherStateMock = Mockito.mock(State::class.java)
        val aThirdStateMock = Mockito.mock(State::class.java)
        val transitionMock = Mockito.mock(TransitionCondition::class.java)
        Mockito.`when`(transitionMock.met()).thenReturn(true)

        val stateMachine = StateMachine {
            initialState("initial", initialStateMock).withTransitions {
                to("another").whenJust(transitionMock)
            }
            addState("another", anotherStateMock).withTransitions {
                to("aThird").whenJust(transitionMock)
            }
            addState("aThird", aThirdStateMock)
        }

        stateMachine.update()
        Mockito.verify(initialStateMock).exit()
        Mockito.verify(anotherStateMock).enter()
        Mockito.verify(anotherStateMock).update()
        stateMachine.update()
        Mockito.verify(anotherStateMock).exit()
        Mockito.verify(aThirdStateMock).enter()
        Mockito.verify(aThirdStateMock).update()
    }

    @Test
    fun doesNotTransitionsStatesWhenConditionNotMet() {
        val initialStateMock = Mockito.mock(State::class.java)
        val anotherStateMock = Mockito.mock(State::class.java)
        val transitionMock = Mockito.mock(TransitionCondition::class.java)
        Mockito.`when`(transitionMock.met()).thenReturn(false)

        val stateMachine = StateMachine {
            initialState("initial", initialStateMock).withTransitions {
                to("another").whenJust(transitionMock)
            }
            addState("another", anotherStateMock)
        }

        stateMachine.update()
        Mockito.verify(initialStateMock).update()
        Mockito.verify(initialStateMock, Mockito.never()).exit()
        Mockito.verify(anotherStateMock, Mockito.never()).enter()
        Mockito.verify(anotherStateMock, Mockito.never()).update()
    }
}