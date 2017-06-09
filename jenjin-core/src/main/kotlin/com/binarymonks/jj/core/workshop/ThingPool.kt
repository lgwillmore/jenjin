package com.binarymonks.jj.core.workshop

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.things.Thing


class ThingPool {

    private var thingByXandYandID: ObjectMap<String, Array<Thing>> = ObjectMap()


    fun get(scaleX: Float, scaleY: Float, thingSpecID: Int): Thing? {
        val key = key(scaleX, scaleY, thingSpecID)
        if (thingByXandYandID.containsKey(key))
            return thingByXandYandID.get(key).pop()
        return null
    }

    fun put(scaleX: Float, scaleY: Float, thingSpecID: Int, thing: Thing) {
        val key: String = key(scaleX, scaleY, thingSpecID)
        if (!thingByXandYandID.containsKey(key)) {
            thingByXandYandID.put(key, Array())
        }
        thingByXandYandID.get(key).add(thing)
    }

    private fun key(scaleX: Float, scaleY: Float, thingSpecID: Int): String {
        return "$scaleX|$scaleY|$thingSpecID"
    }

}