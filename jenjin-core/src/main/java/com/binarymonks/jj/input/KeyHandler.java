package com.binarymonks.jj.input;

@FunctionalInterface
public interface KeyHandler {

    boolean handle(Actions.Key keyAction);
}
