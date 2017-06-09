package com.binarymonks.jj.core.async

abstract class PerpetualTask : Task {

    override fun tearDown() {}

    override val isDone: Boolean
        get() = false
}
