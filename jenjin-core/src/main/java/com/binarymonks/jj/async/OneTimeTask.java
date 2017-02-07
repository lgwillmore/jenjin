package com.binarymonks.jj.async;

public abstract class OneTimeTask implements Task {

    @Override
    public void getReady() {

    }

    @Override
    public void doWork() {

    }

    @Override
    public void tearDown() {
        doOnce();
    }

    protected abstract void doOnce();

    @Override
    public boolean isDone() {
        return true;
    }
}
