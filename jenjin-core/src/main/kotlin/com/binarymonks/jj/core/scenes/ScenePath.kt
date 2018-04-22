package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.Copyable
import com.binarymonks.jj.core.copy
import com.binarymonks.jj.core.pools.Poolable
import com.binarymonks.jj.core.pools.new


class ScenePath() : Poolable, Copyable<ScenePath> {

    var path: Array<String> = Array()

    companion object {
        val UP: String = ".."
    }

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

    fun pop(): String {
        return path.pop()
    }

    fun child(childName: String): ScenePath {
        path.add(childName)
        return this
    }

    fun up(): ScenePath {
        path.add(UP)
        return this
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ScenePath) return false

        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        return path.hashCode()
    }

    override fun toString(): String {
        return "ScenePath(path=$path)"
    }

    override fun clone(): ScenePath {
        return copy(this)
    }


}
