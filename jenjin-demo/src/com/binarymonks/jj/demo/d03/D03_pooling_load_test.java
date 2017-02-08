package com.binarymonks.jj.demo.d03;

import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.render.specs.B2DRenderSpec;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

public class D03_pooling_load_test extends Game {
    float WORLD_WIDTH = 100;
    float WORLD_HEIGHT = 100;


    @Override
    protected void gameOn() {
        GameRenderingLayer gameRenderingLayer = new GameRenderingLayer(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        gameRenderingLayer.setDebug(true);
        JJ.layers.addLayerTop(gameRenderingLayer);
    }


    private ThingSpec square() {
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        new B2DRenderSpec()
                                                .color.delegateToProperty("color")
                                )
                                .addPhysics(new FixtureNodeSpec().setShape(new B2DShapeSpec.PolygonRectangle(5, 5)))
                )
//                .addBehaviour()
                ;
    }
}
