package com.binarymonks.jj.specs;


import com.binarymonks.jj.things.Thing;

public class PropField<VALUE> {

    VALUE value;
    String propertyDelegate;
    Thing parent;


    public PropField() {
    }


    public PropField(VALUE value) {
        this.value = value;
    }

    public PropField(SpecPropField<VALUE, ?> specField) {
        this.value = specField.value;
        this.propertyDelegate = specField.propertyDelegate;
    }

    public VALUE get() {
        if (propertyDelegate != null) {
            return (VALUE) parent.getProperty(propertyDelegate);
        }
        return value;
    }

    public void setParent(Thing parent) {
        this.parent = parent;
    }
}
