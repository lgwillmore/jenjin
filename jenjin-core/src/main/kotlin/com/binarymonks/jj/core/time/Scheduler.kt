package com.binarymonks.jj.core.time

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.pools.Poolable
import com.binarymonks.jj.core.pools.new
import com.binarymonks.jj.core.pools.recycle


class Scheduler(val timeFunction: () -> Float) {
    var idCounter = 0
    val scheduledFunctions: ObjectMap<Int, FunctionTracker> = ObjectMap()
    private val removals: Array<Int> = Array()

    fun update() {
        val time = timeFunction.invoke()
        scheduledFunctions.forEach {
            if (it.value.call(time)) {
                removals.add(it.key)
            }
        }
        removals.forEach {
            recycle(scheduledFunctions.remove(it))
        }
        removals.clear()
    }

    fun schedule(
            function: () -> Unit,
            delaySeconds: Float,
            repeat: Int = 1
    ): Int {
        idCounter++
        scheduledFunctions.put(
                idCounter,
                new(FunctionTracker::class).set(function, delaySeconds, timeFunction.invoke(), repeat)
        )
        return idCounter
    }

    fun cancel(id: Int) {
        if (scheduledFunctions.containsKey(id)) {
            recycle(scheduledFunctions.remove(id))
        }
    }
}

val doNothing: () -> Unit = {}

class FunctionTracker : Poolable {

    private var function: () -> Unit = doNothing
    private var scheduledAt = 0f
    private var delay = 0f
    private var repeat = 1
    private var callCount = 0


    fun call(time: Float): Boolean {
        val elapsed = time - scheduledAt
        if (elapsed / delay >= callCount + 1) {
            function.invoke()
            callCount++
        }
        if (repeat == 0) {
            return false
        }
        when (callCount) {
            repeat -> return true
            else -> return false
        }
    }


    override fun reset() {
        function = doNothing
        delay = 0f
        scheduledAt = 0f
        repeat = 1
        callCount = 0
    }

    fun set(function: () -> Unit, delay: Float, time: Float, repeat: Int): FunctionTracker {
        this.function = function
        this.delay = delay
        this.scheduledAt = time
        this.repeat = repeat
        return this
    }

}