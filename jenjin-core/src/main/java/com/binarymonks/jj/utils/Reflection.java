package com.binarymonks.jj.utils;

import java.lang.reflect.Field;

/**
 * Created by lwillmore on 07/02/17.
 */
public class Reflection {

    public static Field getField(String name, Class<?> clazz) {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("Could not get field name %s from class %s", name, clazz.getCanonicalName()));
        }
    }

}
