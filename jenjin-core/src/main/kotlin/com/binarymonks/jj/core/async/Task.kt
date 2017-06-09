package com.binarymonks.jj.core.async

interface Task {

    fun getReady()

    fun doWork()

    fun tearDown()

    val isDone: Boolean

}
