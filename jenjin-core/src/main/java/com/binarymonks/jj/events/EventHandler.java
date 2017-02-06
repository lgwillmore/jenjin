package com.binarymonks.jj.events;

import com.badlogic.gdx.utils.ObjectMap;

@FunctionalInterface
public interface EventHandler {

    void handle(ObjectMap<String, Object> eventParams);

}
