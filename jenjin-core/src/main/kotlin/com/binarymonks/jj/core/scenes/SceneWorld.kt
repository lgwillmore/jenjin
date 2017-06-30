package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.JJ

class SceneWorld {

    internal var scenes = ObjectMap<Int, Scene>(200)
    internal var queuedForAddScenes = ObjectMap<Int, Scene>(100)
    internal var namedScenes = ObjectMap<String, Scene>(10)
    internal var removals = Array<Scene>()
    internal var inUpdate = false

    fun add(scene: Scene) {
        if (inUpdate) {
            queuedForAddScenes.put(scene.id, scene)
        } else {
            reallyAdd(scene)
        }
    }

    private fun reallyAdd(scene: Scene) {
        scenes.put(scene.id, scene)
        if (scene.uniqueName != null) {
            if (namedScenes.containsKey(scene.uniqueName)) {
                throw Exception("Unique named Scene ${scene.uniqueName} already exists.")
            }
            namedScenes.put(scene.uniqueName, scene)
        }
    }

    fun getSceneByUniqueName(uniqueName: String): Scene {
        return namedScenes.get(uniqueName)
    }

    fun update() {
        for (removal in removals) {
            reallyRemove(removal)
        }
        removals.clear()
        for (sceneEntry in queuedForAddScenes) {
            reallyAdd(sceneEntry.value)
        }
        queuedForAddScenes.clear()
        inUpdate = true
        for (sceneEntry in scenes) {
            sceneEntry.value.update()
        }
        inUpdate = false
    }

    private fun reallyRemove(removal: Scene) {
        scenes.remove(removal.id)
        if (removal.uniqueName != null) {
            namedScenes.remove(removal.uniqueName)
        }
        JJ.B.renderWorld.removeScene(removal)
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
