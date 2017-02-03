package com.binarymonks.jj.events;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;
import com.binarymonks.jj.pools.Poolable;

public class Event implements Poolable {
    public Enum<?> eenum;
    public ObjectMap<String, Object> props = new ObjectMap<>();

    Event() {

    }

    public static Event New() {
        return N.ew(Event.class);
    }

    @Override
    public void reset() {
        eenum = null;
        props.clear();
    }
}
