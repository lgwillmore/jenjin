package com.binarymonks.jj.core.api

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.events.EventBus
import com.binarymonks.jj.core.events.ParamSubscriber
import com.binarymonks.jj.core.events.Subscriber


interface EventsAPI {
    /**
     * Lets you register subscribers to events and to send events.
     */

    /**
     * Register a subscriber that does not care about event parameters.
     * Will still be called even if the event does have parameters.
     *
     * @property message The message of the event that you want to subscribe to.
     * @property function The handler of the event.
     *
     * @return Int : The registered ID
     */
    fun register(message: String, function: Subscriber): Int

    /**
     * Register a subscriber that does care about event parameters.
     * Will still be called even if the event does not have parameters.
     *
     * @property message The message of the event that you want to subscribe to.
     * @property function The handler of the event.
     *
     * @return Int : The registered ID
     */
    fun register(message: String, eventHandler: ParamSubscriber): Int

    /**
     * Lets you send an event with or without parameters
     */
    fun send(message: String, eventParams: ObjectMap<String, Any> = EventBus.NULLPARAMS)

}