package com.binarymonks.jj.components;

import com.binarymonks.jj.specs.PropField;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.utils.Reflection;

import java.lang.reflect.Field;

public abstract class Component {

    protected Thing parent;

    public abstract Component clone();

    public <T extends Component> Class<T> type() {
        return (Class<T>) this.getClass();
    }

    public abstract void doWork();

    public abstract void tearDown();

    public abstract void getReady();

    public Thing getParent() {
        return parent;
    }

    public boolean isDone() {
        return false;
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
