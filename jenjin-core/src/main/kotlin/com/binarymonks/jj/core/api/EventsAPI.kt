package com.binarymonks.jj.core.api

import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.events.EventBus
import com.binarymonks.jj.core.events.ParamSubscriber
import com.binarymonks.jj.core.events.Subscriber


interface EventsAPI {

    fun register(message: String, function: Subscriber)

    fun register(message: String, eventHandler: ParamSubscriber)

    fun send(message: String, eventParams: ObjectMap<String, Any> = EventBus.NULLPARAMS)

}