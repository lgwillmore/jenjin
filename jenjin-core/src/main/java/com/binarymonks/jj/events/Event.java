package com.binarymonks.jj.events;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;

public class Event {
    public Enum<?> eenum;
    public ObjectMap<String, Object> props = new ObjectMap<>();

    Event() {

    }

    public static Event New() {
        return N.ew(Event.class);
    }

    public static class PM implements PoolManager<Event> {
        @Override
        public void reset(Event event) {
            event.eenum = null;
            event.props.clear();
        }

        @Override
        public Event create_new() {
            return new Event();
        }
    }
}
