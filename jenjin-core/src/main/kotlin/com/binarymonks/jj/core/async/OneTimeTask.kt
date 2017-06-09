package com.binarymonks.jj.core.async

abstract class OneTimeTask : Task {

    override fun getReady() {

    }

    override fun doWork() {

    }

    override fun tearDown() {
        doOnce()
    }

    protected abstract fun doOnce()

    override val isDone: Boolean
        get() = true
}
