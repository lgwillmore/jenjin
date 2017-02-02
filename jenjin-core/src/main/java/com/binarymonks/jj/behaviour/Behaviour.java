package com.binarymonks.jj.behaviour;

import com.binarymonks.jj.things.Thing;

public abstract class Behaviour {

    public String name;

    public Thing parent;

    public abstract void update();

    public abstract Behaviour clone();


}
