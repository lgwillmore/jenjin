package com.binarymonks.jj.core.workshop

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.scenes.Scene


class ScenePool {

    private var sceneByXandYandID: ObjectMap<String, Array<Scene>> = ObjectMap()

    fun get(scaleX: Float, scaleY: Float, thingSpecID: Int): Scene? {
        val key = key(scaleX, scaleY, thingSpecID)
        if (sceneByXandYandID.containsKey(key))
            return sceneByXandYandID.get(key).pop()
        return null
    }

    fun put(scaleX: Float, scaleY: Float, thingSpecID: Int, scene: Scene) {
        val key: String = key(scaleX, scaleY, thingSpecID)
        if (!sceneByXandYandID.containsKey(key)) {
            sceneByXandYandID.put(key, Array())
        }
        sceneByXandYandID.get(key).add(scene)
    }

    private fun key(scaleX: Float, scaleY: Float, thingSpecID: Int): String {
        return "$scaleX|$scaleY|$thingSpecID"
    }

}