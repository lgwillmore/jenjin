package com.binarymonks.jj.specs;

/**
 * Created by lwillmore on 06/02/17.
 */
public interface FieldPropertyDelegate<VALUE> {
    void set(VALUE value);

    void delegateToProperty(String propertykey);
}
