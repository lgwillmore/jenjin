package com.binarymonks.jj.events;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;

public class Event {
    public Enum<?> eenum;
    public ObjectMap<String, Object> props = new ObjectMap<>();

    /**
     * Convenience method for getting new Pooled Event.
     * This will be recycled by the {@link com.binarymonks.jj.events.EventBus} after delivery.
     * @return Event
     */
    public static Event newFromPool() {
        return JJ.pools.nuw(Event.class);
    }
}
