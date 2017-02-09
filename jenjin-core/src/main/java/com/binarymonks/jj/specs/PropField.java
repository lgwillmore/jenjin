package com.binarymonks.jj.specs;


import com.binarymonks.jj.physics.collisions.EmitEventCollision;
import com.binarymonks.jj.things.Thing;

public class PropField<VALUE, OWNER> implements FieldPropertyDelegate<VALUE, OWNER> {

    VALUE value;
    OWNER owner;
    String propertyDelegate;
    Thing parent;


    public PropField(OWNER owner) {
        this.owner = owner;
    }

    public PropField(OWNER owner, VALUE value) {
        this.value = value;
        this.owner = owner;
    }

    public void copyFrom(SpecPropField<VALUE, ?> specField) {
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

    @Override
    public OWNER set(VALUE value) {
        this.value = value;
        return owner;
    }

    @Override
    public OWNER delegateToProperty(String propertykey) {
        this.propertyDelegate = propertykey;
        return owner;
    }

    public void copyFrom(PropField<VALUE, ?> specField) {
        this.value = specField.value;
        this.propertyDelegate = specField.propertyDelegate;
    }
}
