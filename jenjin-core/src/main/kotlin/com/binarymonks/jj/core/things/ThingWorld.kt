package com.binarymonks.jj.core.things

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ

class ThingWorld {

    internal var things = ObjectMap<Int, Thing>(200)
    internal var queuedForAddThings = ObjectMap<Int, Thing>(100)
    internal var namedThings = ObjectMap<String, Thing>(10)
    internal var removals = Array<Thing>()
    internal var inUpdate = false

    fun add(thing: Thing) {
        if (inUpdate) {
            queuedForAddThings.put(thing.id, thing)
        } else {
            reallyAdd(thing)
        }
    }

    private fun reallyAdd(thing: Thing) {
        things.put(thing.id, thing)
        if (thing.uniqueName != null) {
            if (namedThings.containsKey(thing.uniqueName)) {
                throw Exception("Unique named Thing ${thing.uniqueName} already exists.")
            }
            namedThings.put(thing.uniqueName, thing)
        }
    }

    fun getThingByUniqueName(uniqueName: String): Thing {
        return namedThings.get(uniqueName)
    }

    fun update() {
        for (removal in removals) {
            reallyRemove(removal)
        }
        removals.clear()
        for (thingEntry in queuedForAddThings) {
            reallyAdd(thingEntry.value)
        }
        queuedForAddThings.clear()
        inUpdate = true
        for (thingEntry in things) {
            thingEntry.value.update()
        }
        inUpdate = false
    }

    private fun reallyRemove(removal: Thing) {
        things.remove(removal.id)
        if (removal.uniqueName != null) {
            namedThings.remove(removal.uniqueName)
        }
        JJ.B.renderWorld.removeThing(removal)
        removal.executeDestruction()
    }

    fun remove(thing: Thing) {
        if (inUpdate) {
            removals.add(thing)
        } else {
            reallyRemove(thing)
        }
    }
}
