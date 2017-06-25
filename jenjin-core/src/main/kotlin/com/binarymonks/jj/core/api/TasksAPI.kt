package com.binarymonks.jj.core.api

import com.binarymonks.jj.core.async.Task

interface TasksAPI {

    fun addPreLoopTask(task: Task)

    fun addPostPhysicsTask(task: Task)

    fun addPrePhysicsTask(task: Task)

}
