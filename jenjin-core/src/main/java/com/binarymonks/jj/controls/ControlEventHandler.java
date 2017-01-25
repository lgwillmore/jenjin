package com.binarymonks.jj.controls;

@FunctionalInterface
public interface ControlEventHandler<EVENT> {
    void handle(EVENT event);
}
