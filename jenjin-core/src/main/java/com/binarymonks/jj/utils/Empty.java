package com.binarymonks.jj.utils;

import com.badlogic.gdx.utils.Array;

public class Empty {

    static Array<?> array = new Array<>();

    public static <T> Array<T> Array() {
        return (Array<T>) array;
    }
}
