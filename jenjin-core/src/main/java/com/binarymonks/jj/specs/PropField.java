package com.binarymonks.jj.specs;


import com.binarymonks.jj.things.Thing;

public class PropField<VALUE> {

    VALUE value;
    String propertyDelegate;
    Thing parent;

    public VALUE get() {
        if (propertyDelegate != null) {
            return (VALUE) parent.getProperty(propertyDelegate);
        }
        return value;
    }
}
