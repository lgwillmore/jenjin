package com.binarymonks.jj.behaviour;

import com.binarymonks.jj.things.Thing;

public abstract class Behaviour {

    protected Thing parent;

    public abstract void update();

    public abstract Behaviour clone();

    public Class<?> type() {
        return this.getClass();
    }

    public Thing getParent() {
        return parent;
    }

    public void setParent(Thing parent) {
        this.parent = parent;
    }
}
