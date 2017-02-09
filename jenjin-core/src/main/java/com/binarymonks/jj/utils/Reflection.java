package com.binarymonks.jj.utils;

import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.specs.PropField;

import java.lang.reflect.Field;

/**
 * Created by lwillmore on 07/02/17.
 */
public class Reflection {

    public static Field getField(String name, Class<?> clazz) {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("Could not checkPools field name %s from class %s", name, clazz.getCanonicalName()));
        }
    }


    public static <T> T getFieldFromInstance(Field field, Object parentObject) {
        try {
            return (T) field.get(parentObject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("Could not checkPools the field value %s from class %s", field.getName(), parentObject.getClass().getCanonicalName()));
        }
    }
}
