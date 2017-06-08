package com.binarymonks.jj.core.api

interface ClockAPI {

    val delta: Double

    val deltaFloat: Float

    val time: Double

    val timeFloat: Float

    fun schedule(function: () -> Unit, delaySeconds: Float, repeat: Int = 1): Int

    fun cancel(id: Int)
}
