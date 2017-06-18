package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ

class SceneWorld {

    internal var things = ObjectMap<Int, Scene>(200)
    internal var queuedForAddThings = ObjectMap<Int, Scene>(100)
    internal var namedThings = ObjectMap<String, Scene>(10)
    internal var removals = Array<Scene>()
    internal var inUpdate = false

    fun add(scene: Scene) {
        if (inUpdate) {
            queuedForAddThings.put(scene.id, scene)
        } else {
            reallyAdd(scene)
        }
    }

    private fun reallyAdd(scene: Scene) {
        things.put(scene.id, scene)
        if (scene.uniqueName != null) {
            if (namedThings.containsKey(scene.uniqueName)) {
                throw Exception("Unique named Scene ${scene.uniqueName} already exists.")
            }
            namedThings.put(scene.uniqueName, scene)
        }
    }

    fun getThingByUniqueName(uniqueName: String): Scene {
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

    private fun reallyRemove(removal: Scene) {
        things.remove(removal.id)
        if (removal.uniqueName != null) {
            namedThings.remove(removal.uniqueName)
        }
        JJ.B.renderWorld.removeThing(removal)
        removal.executeDestruction()
    }

    fun remove(scene: Scene) {
        if (inUpdate) {
            removals.add(scene)
        } else {
            reallyRemove(scene)
        }
    }
}
