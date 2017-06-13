package com.binarymonks.jj.core.scenes

import com.badlogic.gdx.utils.Array
import com.binarymonks.jj.core.pools.Poolable
import com.binarymonks.jj.core.things.Thing

val UP: String = ".."

class ScenePath() : Poolable{

    internal val path: Array<String> = Array()

    constructor(vararg pathElements: String) : this() {
        pathElements.forEach { path.add(it) }
    }

    fun build(vararg pathElements: String): ScenePath{
        reset()
        pathElements.forEach { path.add(it) }
        return this
    }

    fun from(thing: Thing): Thing {
        return from(thing, 0)
    }

    private fun from(thing: Thing?, offset: Int): Thing {
        if (thing == null) {
            throw Exception("Invalid Path")
        }
        if (offset == path.size) {
            return thing
        }
        when (path[offset]) {
            UP -> return from(thing.parent(), offset + 1)
            else -> return from(thing.getChild(path[offset]), offset + 1)
        }
    }

    override fun reset() {
        path.clear()
    }
}
