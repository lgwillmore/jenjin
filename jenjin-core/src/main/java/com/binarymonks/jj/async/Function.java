package com.binarymonks.jj.async;


@FunctionalInterface
public interface Function {
    void call();

    static void doNothing() {

    }
}
