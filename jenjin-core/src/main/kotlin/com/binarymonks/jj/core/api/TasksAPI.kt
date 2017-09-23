package com.binarymonks.jj.core.api

import com.binarymonks.jj.core.async.FunctionClosureBuilder
import com.binarymonks.jj.core.async.Task
import kotlin.reflect.KFunction

interface TasksAPI {

    fun addPreLoopTask(task: Task)

    fun addPostPhysicsTask(task: Task)

    fun doOnceAfterPhysics(fn: () -> Unit)

    fun doOnceAfterPhysicsCapture(function: KFunction<*>, build: (FunctionClosureBuilder.() -> Unit)? = null)

    fun addPrePhysicsTask(task: Task)

}
