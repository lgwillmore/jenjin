package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.pools.Poolable

val UP: String = ".."

class ScenePath() : Poolable {

    internal val path: Array<String> = Array()

    constructor(vararg pathElements: String) : this() {
        pathElements.forEach { path.add(it) }
    }

    constructor(path: Array<String>) : this() {
        path.forEach { this.path.add(it) }
    }

    fun build(vararg pathElements: String): ScenePath {
        reset()
        pathElements.forEach { path.add(it) }
        return this
    }

    fun build(path: Array<String>): ScenePath {
        reset()
        path.forEach { this.path.add(it) }
        return this
    }

    fun child(childName: String) {
        path.add(childName)
    }

    fun up() {
        path.add(UP)
    }

    fun from(scene: Scene): Scene {
        return from(scene, 0)
    }

    private fun from(scene: Scene?, offset: Int): Scene {
        if (scene == null) {
            throw Exception("Invalid Path")
        }
        if (offset == path.size) {
            return scene
        }
        when (path[offset]) {
            UP -> return from(scene.parent(), offset + 1)
            else -> return from(scene.getChild(path[offset]), offset + 1)
        }
    }

    override fun reset() {
        path.clear()
    }
}
