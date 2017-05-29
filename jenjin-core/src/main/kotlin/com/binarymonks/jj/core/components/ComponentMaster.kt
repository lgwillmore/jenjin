package com.binarymonks.jj.core.components

import com.badlogic.gdx.utils.ObjectMap
import kotlin.reflect.KClass

class ComponentMaster {

    private var trackedComponents = ObjectMap<KClass<Component>, Component>()
    private var addTrackedComponent = ObjectMap<KClass<Component>, Component>()
    private var removeTrackedComponents = ObjectMap<KClass<Component>, Component>()

    fun update() {
        clean()
        for (entry in trackedComponents.entries()) {
            if (entry.value.isDone()) {
                removeTrackedComponents.put(entry.key, entry.value)
            } else {
                entry.value.update()
            }
        }
    }

    fun clean() {
        for (entry in removeTrackedComponents.entries()) {
            entry.value.onRemoveFromWorld()
            trackedComponents.remove(entry.key)
        }
        removeTrackedComponents.clear()
        for (entry in addTrackedComponent.entries()) {
            trackedComponents.put(entry.key, entry.value)
            entry.value.onAddToWorld()
        }
        addTrackedComponent.clear()
    }

    fun neutralise() {
        clean()
        for (entry in trackedComponents.entries()) {
            entry.value.onRemoveFromWorld()
        }
    }

    fun reactivate() {
        for (entry in trackedComponents.entries()) {
            entry.value.onAddToWorld()
        }
    }

    fun addComponent(component: Component) {
        if (!component.type().java.isAssignableFrom(component.javaClass)) {
            throw Exception("Your component ${component::class.simpleName} is not an instance of its type")
        }
        if (trackedComponents.containsKey(component.type()) || addTrackedComponent.containsKey(component.type())) {
            throw RuntimeException("Your are adding a tracked component that will clobber another component of type ${component.type().simpleName}")
        }
        addTrackedComponent.put(component.type(), component)
    }

    fun <T : Component> getComponent(type: KClass<Component>): T? {
        if (trackedComponents.containsKey(type)) {
            return trackedComponents.get(type) as T
        }
        if (addTrackedComponent.containsKey(type)) {
            return addTrackedComponent.get(type) as T
        }
        return null
    }

}
