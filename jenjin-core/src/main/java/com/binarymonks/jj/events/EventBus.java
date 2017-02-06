package com.binarymonks.jj.events;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.async.Function;

public class EventBus {

    static ObjectMap<String, Object> NULLPARAMS = new ObjectMap<>();

    ObjectMap<String, Listeners> listeners = new ObjectMap<>();

    public void register(String message, Function function) {
        if (!listeners.containsKey(message)) {
            listeners.put(message, new Listeners());
        }
        listeners.get(message).funcListeners.add(function);
    }

    public void register(String message, EventHandler eventHandler) {
        if (!listeners.containsKey(message)) {
            listeners.put(message, new Listeners());
        }
        listeners.get(message).handlers.add(eventHandler);
    }

    public void send(String message, ObjectMap<String, Object> eventParams) {
        if (listeners.containsKey(message)) {
            for (Function func : listeners.get(message).funcListeners) {
                func.call();
            }
            for (EventHandler handler : listeners.get(message).handlers) {
                handler.handle(eventParams);
            }
        }
    }

    public void send(String message) {
        send(message, NULLPARAMS);
    }

    public static class Listeners {
        Array<Function> funcListeners = new Array<>();
        Array<EventHandler> handlers = new Array<>();
    }

}
