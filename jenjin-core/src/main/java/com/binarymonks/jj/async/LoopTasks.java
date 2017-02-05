package com.binarymonks.jj.async;

import com.binarymonks.jj.api.Tasks;

public class LoopTasks implements Tasks {

    public TaskMaster preloopTasks = new TaskMaster();

    @Override
    public void addPreLoopTask(Task task) {
        preloopTasks.addTask(task);
    }
}
