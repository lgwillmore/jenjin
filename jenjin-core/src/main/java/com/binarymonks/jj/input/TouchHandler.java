package com.binarymonks.jj.input;

import com.badlogic.gdx.math.Vector2;


/**
 * A functional interface for handling the updates from touch events.
 * The updates will occur on touch down and touch move. Null will be passed in as a signal of touch up.
 */
@FunctionalInterface
public interface TouchHandler {

    void updateTouch(Vector2 worldCoords);

}
