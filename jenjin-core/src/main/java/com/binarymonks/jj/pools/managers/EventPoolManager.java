package com.binarymonks.jj.pools.managers;

import com.binarymonks.jj.events.Event;
import com.binarymonks.jj.pools.PoolManager;

public class EventPoolManager implements PoolManager<Event> {
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
