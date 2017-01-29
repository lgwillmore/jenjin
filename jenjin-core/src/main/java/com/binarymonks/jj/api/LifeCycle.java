package com.binarymonks.jj.api;

import com.binarymonks.jj.lifecycle.LifeCycleAware;

public interface LifeCycle {
    void register(LifeCycleAware subscriber);

    void deregister(LifeCycleAware subscriber);
}
