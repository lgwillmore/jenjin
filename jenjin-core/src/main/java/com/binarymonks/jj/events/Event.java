package com.binarymonks.jj.events;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.pools.N;

public class Event {
    public Enum<?> eenum;
    public ObjectMap<String, Object> props = new ObjectMap<>();

    Event() {

    }

    public static Event New() {
        return JJ.pools.nuw(Event.class);
    }
}
