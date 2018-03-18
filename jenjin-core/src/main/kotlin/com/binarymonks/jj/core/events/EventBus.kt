package com.binarymonks.jj.core.events

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.api.EventsAPI

typealias Subscriber = () -> Unit
typealias ParamSubscriber = (params: ObjectMap<String, Any>) -> Unit


class EventBus : EventsAPI {

    internal var listeners = ObjectMap<String, Listeners>()
    private var idCounter = 0

    private fun nextID(): Int {
        idCounter++
        return idCounter
    }

    override fun register(message: String, function: Subscriber): Int {
        if (!listeners.containsKey(message)) {
            listeners.put(message, Listeners())
        }
        val id = nextID()
        listeners.get(message).funcListeners.put(id, function)
        return id
    }

    override fun register(message: String, eventHandler: ParamSubscriber): Int {
        if (!listeners.containsKey(message)) {
            listeners.put(message, Listeners())
        }
        val id = nextID()
        listeners.get(message).handlers.put(id, eventHandler)
        return id
    }

    override fun send(message: String, eventParams: ObjectMap<String, Any>) {
        if (listeners.containsKey(message)) {
            for (func in listeners.get(message).funcListeners.values()) {
                func()
            }
            for (handler in listeners.get(message).handlers.values()) {
                handler(eventParams)
            }
        }
    }

    fun deregister(message: String, registerID: Int) {
        if (listeners.containsKey(message)) {
            listeners[message].funcListeners.remove(registerID)
            listeners[message].handlers.remove(registerID)
        }
    }

    fun clear() {
        listeners.forEach { it.value.funcListeners.clear(); it.value.handlers.clear() }
    }

    class Listeners {
        internal var funcListeners = ObjectMap<Int, Subscriber>()
        internal var handlers = ObjectMap<Int, ParamSubscriber>()
    }

    companion object {

        internal var NULLPARAMS = ObjectMap<String, Any>()
    }

}
