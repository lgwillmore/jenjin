package com.binarymonks.jj.api;

import com.binarymonks.jj.input.Actions;
import com.binarymonks.jj.things.Thing;

import java.util.function.Supplier;

/**
 * Created by lwillmore on 10/02/17.
 */
public interface JJInput {
    void map(int keyCode, Actions.Key keyAction, Supplier<Boolean> function);

    void addMouseDrag(Thing thing);
}
