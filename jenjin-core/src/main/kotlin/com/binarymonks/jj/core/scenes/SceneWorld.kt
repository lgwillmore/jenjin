package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.pools.newArray
import com.binarymonks.jj.core.pools.recycle

class SceneWorld {

    internal var rootScene: Scene? = null
    internal var namedScenes = ObjectMap<String, Scene>(10)
    internal var inUpdate = false

    fun getSceneByUniqueName(uniqueName: String): Scene {
        return namedScenes.get(uniqueName)
    }

    fun update() {
        inUpdate = true
        rootScene?.update()
        inUpdate = false
    }

    fun destroyAllScenes() {
        for(entry in rootScene!!.sceneLayers.entries()){
            val layerCopy: Array<Scene> = newArray()
            for(scene in entry.value){
                layerCopy.add(scene)
            }
            for(scene in layerCopy){
                scene.destroy()
            }
            recycle(layerCopy)
        }
    }
}
