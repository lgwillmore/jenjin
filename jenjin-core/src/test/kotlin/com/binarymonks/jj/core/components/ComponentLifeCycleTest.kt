package com.binarymonks.jj.core.components

import com.binarymonks.jj.core.scenes.Scene
import com.binarymonks.jj.core.testScene
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ComponentLifeCycleTest {


    lateinit var mockComponent: Component

    lateinit var scene: Scene


    @Before
    fun setUp() {
        scene = testScene()
        mockComponent = Mockito.mock(Component::class.java)
        Mockito.`when`(mockComponent.type()).thenReturn(Component::class)
    }


    @Test
    fun addToScene_whenSceneIsNotInitiallyAddedToWorld_removed_and_added_again() {

        scene.addComponent(mockComponent)

        Mockito.verify(mockComponent, Mockito.never()).onAddToScene()
        Mockito.verify(mockComponent, Mockito.never()).onAddToWorld()

        scene.onAddToWorld()

        Mockito.verify(mockComponent, Mockito.atMost(1)).onAddToScene()
        Mockito.verify(mockComponent, Mockito.atMost(1)).onAddToWorld()

        scene.executeDestruction()

        Mockito.verify(mockComponent).onRemoveFromWorld()
        Mockito.verify(mockComponent).onScenePool()

        scene.onAddToWorld()

        Mockito.verify(mockComponent, Mockito.atMost(1)).onAddToScene()
        Mockito.verify(mockComponent, Mockito.atLeast(2)).onAddToWorld()

    }

    @Test
    fun addToScene_whenSceneIsInitiallyAddedToWorld() {
        Mockito.`when`(mockComponent.isDone()).thenReturn(true)

        scene.onAddToWorld()
        scene.addComponent(mockComponent)

        Mockito.verify(mockComponent).onAddToScene()
        Mockito.verify(mockComponent).onAddToWorld()
    }

    @Test
    fun addToSceneAfterBeingRemovedFromScene() {
        Mockito.`when`(mockComponent.isDone()).thenReturn(true)

        scene.onAddToWorld()
        scene.addComponent(mockComponent)
        scene.update()
        scene.update()

        Mockito.verify(mockComponent).onRemoveFromScene()

        scene.addComponent(mockComponent)
        Mockito.verify(mockComponent, Mockito.atLeast(2)).onAddToScene()
        Mockito.verify(mockComponent, Mockito.atLeast(2)).onAddToWorld()

    }


}