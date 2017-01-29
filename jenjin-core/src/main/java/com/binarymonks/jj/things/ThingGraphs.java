package com.binarymonks.jj.things;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class ThingGraphs {

    ObjectMap<Integer, Thing> things = new ObjectMap<>(200);
    ObjectMap<Integer, Thing> queuedForAddThings = new ObjectMap<>(100);
    Array<Thing> removals = new Array<>();
    boolean inUpdate = false;

    public void add(Thing thing) {
        if (inUpdate) {
            queuedForAddThings.put(thing.id, thing);
        } else {
            reallyAdd(thing);
        }
    }

    private void reallyAdd(Thing thing) {
        things.put(thing.id, thing);
    }

    public void update() {
        inUpdate = true;
        for (ObjectMap.Entry<Integer, Thing> thingEntry : things) {
            ThingTools.update(thingEntry.value);
        }
        for (ObjectMap.Entry<Integer, Thing> thingEntry : queuedForAddThings) {
            ThingTools.update(thingEntry.value);
            reallyAdd(thingEntry.value);
        }
        queuedForAddThings.clear();
        for (Thing removeal : removals) {
            reallyRemove(removeal);
        }
        removals.clear();
        inUpdate = false;
    }

    private void reallyRemove(Thing removeal) {
        things.remove(removeal.id);
        ThingTools.destroy(removeal);
    }

    public void remove(Thing thing) {
        if (inUpdate) {
            removals.add(thing);
        } else {
            reallyRemove(thing);
        }
    }
}
