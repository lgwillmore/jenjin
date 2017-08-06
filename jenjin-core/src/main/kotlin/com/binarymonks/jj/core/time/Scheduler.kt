package com.binarymonks.jj.core.time

import com.badlogic.gdx.math.MathUtils
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
        clean()
        scheduledFunctions.forEach {
            if (it.value.call(time)) {
                removals.add(it.key)
            }
        }
        clean()
    }

    fun clean() {
        removals.forEach {
            if (scheduledFunctions.containsKey(it)) {
                recycle(scheduledFunctions.remove(it))
            }
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
                new(FunctionTracker::class).set(function, delaySeconds, delaySeconds, timeFunction.invoke(), repeat, idCounter)
        )
        return idCounter
    }

    fun schedule(
            function: () -> Unit,
            delayMinSeconds: Float,
            delayMaxSeconds: Float,
            repeat: Int = 1
    ): Int {
        idCounter++

        scheduledFunctions.put(
                idCounter,
                new(FunctionTracker::class).set(function, delayMinSeconds, delayMaxSeconds, timeFunction.invoke(), repeat, idCounter)
        )
        return idCounter
    }

    fun cancel(id: Int) {
        if (scheduledFunctions.containsKey(id)) {
            removals.add(id)
        }
    }
}

val doNothing: () -> Unit = {}

class FunctionTracker : Poolable {

    private var function: () -> Unit = doNothing
    private var scheduledAt = 0f
    private var delayMin = 0f
    private var delayMax = 0f
    private var actualDelay = 0f
    private var repeat = 1
    private var callCount = 0
    private var scheduleID = -1
    private var finished = false


    fun call(time: Float): Boolean {
        val elapsed = time - scheduledAt
        if (elapsed / actualDelay >= callCount + 1) {
            updateDelay()
            function.invoke()
            callCount++
        }
        if (repeat == 0) {
            return false
        }
        when (callCount) {
            repeat -> {
                finished = true
                return true
            }
            else -> return false
        }
    }


    override fun reset() {
        function = doNothing
        delayMin = 0f
        delayMax = 0f
        actualDelay = 0f
        scheduledAt = 0f
        repeat = 1
        callCount = 0
        finished = false
    }

    fun set(function: () -> Unit, delayMin: Float, delayMax: Float, time: Float, repeat: Int, scheduleID: Int): FunctionTracker {
        this.function = function
        this.delayMin = delayMin
        this.delayMax = delayMax
        this.scheduledAt = time
        this.repeat = repeat
        this.scheduleID = scheduleID
        updateDelay()
        return this
    }

    private fun updateDelay() {
        if (delayMin == delayMax)
            actualDelay = delayMin
        else
            actualDelay = MathUtils.random(delayMin, delayMax)
    }

    fun isFinished(): Boolean {
        return finished
    }

}