package com.binarymonks.jj.specs;

/**
 * Created by lwillmore on 06/02/17.
 */
public interface FieldPropertyDelegate<VALUE, OWNER> {
    OWNER set(VALUE value);

    OWNER delegateTo(String propertykey);
}
