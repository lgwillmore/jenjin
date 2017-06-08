package com.binarymonks.jj.core.components

import com.binarymonks.jj.core.JJ


class SelfDestruct(
        val delaySeconds: Float = 1f
) : Component() {

    internal var scheduleID = -1
    override fun onAddToWorld() {
        scheduleID = JJ.clock.schedule(this::destroy, delaySeconds)
    }

    override fun onRemoveFromWorld() {
        JJ.clock.cancel(scheduleID)
    }

    fun destroy() {
        myThing().destroy()
    }
}