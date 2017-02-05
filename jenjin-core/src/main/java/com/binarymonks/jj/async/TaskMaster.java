package com.binarymonks.jj.async;

import com.badlogic.gdx.utils.Array;

public class TaskMaster {

    Array<Task> tasks = new Array<>();
    Array<Task> addTasks = new Array<>();
    Array<Task> removeTasks = new Array<>();

    public void update() {
        clean();
        for (Task task : tasks) {
            if (task.isDone()) {
                removeTasks.add(task);
            } else {
                task.doWork();
            }
        }
    }

    private void clean() {
        for (Task removeTask : removeTasks) {
            removeTask.tearDown();
            tasks.removeValue(removeTask, true);
        }
        removeTasks.clear();
        for (Task addTask : addTasks) {
            tasks.add(addTask);
            addTask.getReady();
        }
        addTasks.clear();
    }

    public void addTask(Task task) {
        addTasks.add(task);
    }

}
