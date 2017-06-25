package com.binarymonks.jj.core.async

import com.binarymonks.jj.core.api.TasksAPI

class Tasks : TasksAPI {

    internal var preloopTasks = TaskMaster()
    internal var postPhysicsTasks = TaskMaster()
    internal var prePhysicsTasks = TaskMaster()

    override fun addPreLoopTask(task: Task) {
        preloopTasks.addTask(task)
    }

    override fun addPostPhysicsTask(task: Task) {
        postPhysicsTasks.addTask(task)
    }

    override fun addPrePhysicsTask(task: Task) {
        prePhysicsTasks.addTask(task)
    }
}
