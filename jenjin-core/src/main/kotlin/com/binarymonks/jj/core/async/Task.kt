package com.binarymonks.jj.core.async

/**
 * A Task lets you define some work that you want to do over a game loop, or several game loops.
 */
interface Task {

    fun getReady()

    fun doWork()

    fun tearDown()

    fun isDone(): Boolean

}
