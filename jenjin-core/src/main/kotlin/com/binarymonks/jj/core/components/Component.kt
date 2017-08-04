package com.binarymonks.jj.core.components

import com.binarymonks.jj.core.Copyable
import com.binarymonks.jj.core.copy
import com.binarymonks.jj.core.properties.PropOverride
import com.binarymonks.jj.core.scenes.Scene
import kotlin.reflect.KClass
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties

private val propDelegateType = PropOverride::class.createType(listOf(KTypeProjection(null, null)))

abstract class Component : Copyable<Component> {

    open internal var scene: Scene? = null
        set(value) {
            field = value
            this::class.declaredMemberProperties.forEach {
                if (it.returnType.isSubtypeOf(propDelegateType)) {
                    val b = it.name
                    val pd = this.javaClass.kotlin.memberProperties.first { it.name == b }.get(this) as PropOverride<*>
                    pd.hasProps = value
                }
            }
        }

    fun me(): Scene {
        return checkNotNull(scene)
    }

    /**
     * This will be used to create a new instances of your component.
     *
     * Public fields of the component will be copied to the new instance.
     *
     * If it is not a primitive the reference will be shared by the clone unless it is [com.binarymonks.jj.core.Copyable], then clone will be called.
     *
     * If this is not sufficient then override this method.
     */
    override fun clone(): Component {
        return copy(this)
    }

    /**
     * Components are stored and retrieved by their type. It makes no sense to have more than
     * one component of the same type operating on a given [Scene]
     *
     * But you may have several implementations of a type. This lets you specify the key type (or top level interface) that
     * will be used to store and retrieve your [Component] if you need to.
     */
    open fun type(): KClass<Component> {
        return this::class as KClass<Component>
    }

    /**
     * Called when the component is added to a [Scene]. The parent [Scene] will be available, but not necessarily fully
     * initialised.
     */
    open fun onAddToScene() {
    }

    /**
     * This will be called on every game loop. Override this for ongoing tasks
     */
    open fun update() {

    }

    /**
     * Called when the component is removed from the [Scene]. This is called when the component indicates it should be removed
     * via [isDone]
     */
    open fun onRemoveFromScene() {
    }

    /**
     * You can also build short lived components which can be applied to a [Scene] and then identify themselves as
     * done when they have completed their task. They will then be removed from the [Scene]
     */
    open fun isDone(): Boolean {
        return false
    }

    /**
     * Called when the components [Scene] is added to the world. The [Scene] will  have all of its children nodes.
     * It will not have its parent node yet though. Leaves are built first.
     * This is also called when a pooled [Scene] is drawn from the pool. Any resetting of state for the [Component]
     * should be done with this in mind.
     */
    open fun onAddToWorld() {

    }

    /**
     * Called when the components [Scene] is removed from the world. Do any required cleanup here.
     * This is also called when a pooled [Scene] is placed in the pool. Any resetting of state for the [Component]
     * should be done with this in mind.
     */
    open fun onRemoveFromWorld() {

    }

    /**
     * Called when the Component is being updated for the first time.
     * The parent Scene will be available as well as the children scene
     */
    open fun onFullyInitialized() {
    }

}