package com.binarymonks.jj.core.events

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.api.EventsAPI

typealias Subscriber = () -> Unit
typealias ParamSubscriber = (params: ObjectMap<String, Any>) -> Unit


class EventBus : EventsAPI {

    internal var listeners = ObjectMap<String, Listeners>()

    override fun register(message: String, function: Subscriber) {
        if (!listeners.containsKey(message)) {
            listeners.put(message, Listeners())
        }
        listeners.get(message).funcListeners.add(function)
    }

    override fun register(message: String, eventHandler: ParamSubscriber) {
        if (!listeners.containsKey(message)) {
            listeners.put(message, Listeners())
        }
        listeners.get(message).handlers.add(eventHandler)
    }

    override fun send(message: String, eventParams: ObjectMap<String, Any>) {
        if (listeners.containsKey(message)) {
            for (func in listeners.get(message).funcListeners) {
                func()
            }
            for (handler in listeners.get(message).handlers) {
                handler(eventParams)
            }
        }
    }

    class Listeners {
        internal var funcListeners: Array<Subscriber> = Array()
        internal var handlers = Array<ParamSubscriber>()
    }

    companion object {

        internal var NULLPARAMS = ObjectMap<String, Any>()
    }

}
