package com.binarymonks.jj.async;

public interface Task {

    void getReady();

    void doWork();

    void tearDown();

    boolean isDone();

}
