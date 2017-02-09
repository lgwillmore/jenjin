package com.binarymonks.jj.things;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.backend.Global;

public class ThingWorld {

    ObjectMap<Integer, Thing> things = new ObjectMap<>(200);
    ObjectMap<Integer, Thing> queuedForAddThings = new ObjectMap<>(100);
    ObjectMap<String, Thing> namedThings = new ObjectMap<>(10);
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
        if (thing.uniqueName != null) {
            if (namedThings.containsKey(thing.uniqueName)) {
                throw new RuntimeException(String.format("Unique named Thing %s already exists.", thing.uniqueName));
            }
            namedThings.put(thing.uniqueName, thing);
        }
    }

    public Thing getThingByUniqueName(String uniqueName) {
        return namedThings.get(uniqueName);
    }

    public void update() {
        for (Thing removal : removals) {
            reallyRemove(removal);
        }
        removals.clear();
        for (ObjectMap.Entry<Integer, Thing> thingEntry : queuedForAddThings) {
            reallyAdd(thingEntry.value);
        }
        queuedForAddThings.clear();
        inUpdate = true;
        for (ObjectMap.Entry<Integer, Thing> thingEntry : things) {
            thingEntry.value.update();
        }
        inUpdate = false;
    }

    private void reallyRemove(Thing removal) {
        things.remove(removal.id);
        if (removal.uniqueName != null) {
            namedThings.remove(removal.uniqueName);
        }
        Global.renderWorld.removeThing(removal);
        ThingTools.destroy(removal);
    }

    public void remove(Thing thing) {
        if (inUpdate) {
            removals.add(thing);
        } else {
            reallyRemove(thing);
        }
    }
}
