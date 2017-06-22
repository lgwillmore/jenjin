package com.binarymonks.jj.core.components.misc


class SelfDestruct(
        var delaySeconds: Float = 1f
) : com.binarymonks.jj.core.components.Component() {

    internal var scheduleID = -1
    override fun onAddToWorld() {
        scheduleID = com.binarymonks.jj.core.JJ.clock.schedule(this::destroy, delaySeconds)
    }

    override fun onRemoveFromWorld() {
        com.binarymonks.jj.core.JJ.clock.cancel(scheduleID)
    }

    fun destroy() {
        me().destroy()
    }
}