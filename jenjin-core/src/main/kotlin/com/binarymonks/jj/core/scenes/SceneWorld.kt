package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.utils.ObjectMap

class SceneWorld {

    internal var rootScene: Scene? = null
    internal var namedScenes = ObjectMap<String, Scene>(10)
    internal var inUpdate = false

    fun add(scene: Scene, layer: Int = 0) {
        rootScene?.add(scene, layer)
        scene.onAddToWorld()
    }

    fun getSceneByUniqueName(uniqueName: String): Scene {
        return namedScenes.get(uniqueName)
    }

    fun update() {
        inUpdate = true
        rootScene?.update()
        inUpdate = false
    }
}
