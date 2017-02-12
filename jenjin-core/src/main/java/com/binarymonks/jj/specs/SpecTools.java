package com.binarymonks.jj.specs;

import com.badlogic.gdx.utils.ObjectMap;

public class SpecTools {
    public static <T> T freeze(SpecPropField<T, ?> propField, ObjectMap<String, Object> properties) {
        if (propField.propertyDelegate != null) {
            return (T) properties.get(propField.propertyDelegate);
        }
        return propField.value;
    }
}
