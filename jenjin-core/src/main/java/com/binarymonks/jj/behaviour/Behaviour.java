package com.binarymonks.jj.behaviour;

import com.binarymonks.jj.async.Task;
import com.binarymonks.jj.specs.PropField;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.utils.Reflection;

import java.lang.reflect.Field;

public abstract class Behaviour implements Task {

    protected Thing parent;

    public abstract void getReady();

    public abstract void update();

    public abstract void tearDown();

    public abstract Behaviour clone();

    public Class<?> type() {
        return this.getClass();
    }

    public Thing getParent() {
        return parent;
    }

    public void setParent(Thing parent) {
        this.parent = parent;
        for (Field field : this.getClass().getFields()) {
            if (PropField.class.isAssignableFrom(field.getType())) {
                PropField pf = Reflection.getFieldFromInstance(field, this);
                pf.setParent(parent);
            }
        }
    }
}
