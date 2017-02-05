package com.binarymonks.jj.async;

public abstract class PerpetualTask implements Task {

    @Override
    public void tearDown() {
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
