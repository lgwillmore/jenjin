package com.binarymonks.jj.demo.d00;

import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.layers.DefaultLayer;

/**
 * The simplest of the simple - just draw something.
 */
public class D00_Basic extends Game {

    @Override
    protected void gameOn() {
        JJ.layers.addLayerTop(new DefaultLayer());
    }
}
