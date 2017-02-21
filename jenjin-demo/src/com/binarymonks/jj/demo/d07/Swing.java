package com.binarymonks.jj.demo.d07;

import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.layers.GameRenderingLayer;

public class Swing extends Game{

    GameRenderingLayer gameRenderingLayer;
    float WORLD_WIDTH= 500;
    float WORLD_HEIGHT=500;

    @Override
    protected void gameOn() {
        gameRenderingLayer = new GameRenderingLayer(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        JJ.layers.addLayerTop(gameRenderingLayer);
    }
}
