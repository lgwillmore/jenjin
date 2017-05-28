package com.binarymonks.jj.core.components

import com.binarymonks.jj.core.things.Thing
import kotlin.reflect.KClass

abstract class Component {

    internal var parent: Thing? = null

    fun myThing(): Thing{
        return checkNotNull(parent)
    }

    /**
     * This will be used to create a new instances of your component.
     */
    abstract fun clone(): Component

    /**
     * Components are stored and retrieved by their type. It makes no sense to have more than
     * one component of the same type operating on a given [Thing]
     *
     * But you may have several implementations of a type. This lets you specify the key type (or top level interface) that
     * will be used to store and retrieve your [Component] if you need to.
     */
    open fun <T : Component> type(): KClass<out T> {
        return this::class as KClass<out T>
    }

    /**
     * This will be called when a new instance or a de-pooled instance is added to the game world as part of its
     * [Thing] - if your component is not stateless, and your [Thing] is pooled you should re-initialise that state.
     */
    open fun onAddToWorld() {
    }

    /**
     * This will be called on every game loop. Do your work here.
     */
    abstract fun update()

    /**
     * This will be called when the [Thing] is removed from the game world to be pooled/disposed.
     *
     * It will also be called if your [Component] identifies itself as being [Component.isDone]
     *
     * Cleanup any references or state as appropriate.
     */
    open fun onRemoveFromWorld() {
    }

    /**
     * You can also build short lived components which can be applied to a [Thing] and then identify themselves as
     * done when they have completed their task. They will then be removed from the [Thing]
     */
    open fun isDone(): Boolean {
        return false
    }

}