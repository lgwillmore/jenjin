package com.binarymonks.jj.demo.d03;

import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.behaviour.DestroySelf;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.render.specs.B2DRenderSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.SceneSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

public class D03_pooling_load_test extends Game {
    float WORLD_WIDTH = 100;
    float WORLD_HEIGHT = 100;


    @Override
    protected void gameOn() {
        GameRenderingLayer gameRenderingLayer = new GameRenderingLayer(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        gameRenderingLayer.setDebug(false);
        JJ.layers.addLayerTop(gameRenderingLayer);

        JJ.specs
                .set("square", square())
                .set("emitter", emitter())
        ;

        SceneSpec scene = new SceneSpec();
        scene.add("emitter",
                InstanceParams.New().setPosition(WORLD_WIDTH*0.25f, WORLD_HEIGHT*0.25f),
                InstanceParams.New().setPosition(WORLD_WIDTH*0.75f, WORLD_HEIGHT*0.75f)
        );

        JJ.things.loadNow(scene);
    }

    private ThingSpec emitter() {
        return new ThingSpec().addBehaviour(new Emitter().setIntervalSeconds(0.05f).setSpecPath("square"));
    }


    private ThingSpec square() {

        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        new B2DRenderSpec().color.set(Color.BLUE)
                                )
                                .addPhysics(new FixtureNodeSpec().setShape(new B2DShapeSpec.PolygonRectangle(5, 5)))
                )
                .addBehaviour(new DestroySelf().timeToLive.set(4f))
                ;
    }
}
