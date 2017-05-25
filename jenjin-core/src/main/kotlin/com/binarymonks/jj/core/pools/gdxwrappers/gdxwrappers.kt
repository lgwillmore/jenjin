package com.binarymonks.jj.core.pools.gdxwrappers

import com.badlogic.gdx.math.Vector2
import com.binarymonks.jj.core.pools.Poolable
import com.binarymonks.jj.core.specs.InstanceParams
import com.binarymonks.jj.core.pools.recycle as recycleP
import com.binarymonks.jj.core.pools.new as nuw

/**
 * Pooled version of GDX objects.
 *
 * You will probably use a lot of these. It is good practice to thing about creating them and destroying them
 *
 * Use [PoolableWrapper.recycle] when you are done with them
 */
interface PoolableWrapper<T> : Poolable {

    fun recycle()

    fun copy(): T

}


class Vec2 : Vector2, PoolableWrapper<Vec2> {

    private constructor()
    private constructor(x: Float, y: Float) : super(x, y)
    private constructor(v: Vector2) : super(v)

    companion object Factory {
        fun new(): InstanceParams {
            return nuw(InstanceParams::class)
        }
    }

    override fun reset() {
        this.set(0f, 0f)
    }

    override fun recycle() {
        recycleP(this)
    }

    override fun copy() : Vec2 {
//        return nuw(Vec2::class).set(this)
        return this
    }
}


