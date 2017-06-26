package com.binarymonks.jj.core

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmErasure


fun <T : Any> copy(original: T): T {
    val myClass = original::class
    var copy = construct(myClass)
    myClass.memberProperties.forEach {
        try {
            if (it.visibility == KVisibility.PUBLIC && isPublicallyMutable(it)) {
                if (it.returnType.jvmErasure.isSubclassOf(Copyable::class)) {
                    val name = it.name
                    val sourceProperty = original.javaClass.kotlin.memberProperties.first { it.name == name }.get(original) as Copyable<*>
                    val destinationProperty = copy.javaClass.kotlin.memberProperties.first { it.name == name } as KMutableProperty<Any>
                    destinationProperty.setter.call(copy, sourceProperty.copy())
                } else {
                    copyProperty(original, copy, it.name)
                }
            }
        } catch (e: Exception) {
            throw Exception("Could not copy something", e)
        }
    }
    return copy
}

fun isPublicallyMutable(property: KProperty1<*, *>): Boolean {
    if (property is KMutableProperty<*>) {
        if (property.setter.visibility == KVisibility.PUBLIC) {
            return true
        }
        return false
    } else {
        return false
    }
}

fun <T : Any> construct(kClass: KClass<T>): T {
    kClass.constructors.forEach {
        if (it.parameters.isEmpty()) {
            return it.call()
        } else {
            var allParamsHaveDefaults = true
            it.parameters.forEach {
                if (!it.isOptional) {
                    allParamsHaveDefaults = false
                }
            }
            if (allParamsHaveDefaults) {
                return it.callBy(emptyMap())
            }
        }
    }
    throw Exception("No empty or fully optional constructor")
}

fun copyProperty(source: Any, destination: Any, propertyName: String) {
    val sourceProperty = source.javaClass.kotlin.memberProperties.first { it.name == propertyName }.get(source)
    val destinationProperty = destination.javaClass.kotlin.memberProperties.first { it.name == propertyName } as KMutableProperty<Any>
    destinationProperty.setter.call(destination, sourceProperty)
}
