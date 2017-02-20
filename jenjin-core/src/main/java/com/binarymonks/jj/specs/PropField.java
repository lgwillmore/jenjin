package com.binarymonks.jj.specs;


import com.binarymonks.jj.physics.collisions.EmitEventCollision;
import com.binarymonks.jj.things.Thing;

public class PropField<VALUE> implements FieldPropertyDelegate<VALUE> {

    VALUE value;
    String propertyDelegate;
    Thing parent;

    public PropField(){}

    public PropField(VALUE value) {
        this.value = value;
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

    @Override
    public void set(VALUE value) {
        this.value = value;
    }

    @Override
    public void delegateToProperty(String propertykey) {
        this.propertyDelegate = propertykey;
    }

    public void copyFrom(PropField<VALUE> specField) {
        this.value = specField.value;
        this.propertyDelegate = specField.propertyDelegate;
    }

    public void copyFrom(SpecPropField<VALUE> specField) {
        this.value = specField.value;
        this.propertyDelegate = specField.propertyDelegate;
    }
}
