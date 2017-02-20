package com.binarymonks.jj.demo.d03;

import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.behaviour.DestroySelf;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.specs.B2DCompositeSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.FixtureNodeSpec;
import com.binarymonks.jj.specs.render.B2DRenderSpec;
import com.binarymonks.jj.specs.render.RenderBuilder;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.specs.NodeSpec;
import com.binarymonks.jj.specs.ThingSpec;

public class D03_pooling_load_test extends Game {
    float WORLD_WIDTH = 100;
    float WORLD_HEIGHT = 100;

    public D03_pooling_load_test(JJConfig jjConfig) {
        super(jjConfig);
    }


    @Override
    protected void gameOn() {
        GameRenderingLayer gameRenderingLayer = new GameRenderingLayer(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        JJ.layers.addLayerTop(gameRenderingLayer);

        JJ.specs
                .set("square", square())
                .set("emitter", emitter())
        ;

        B2DCompositeSpec scene = new B2DCompositeSpec();
        scene.addThingSpec("emitter", InstanceParams.New().setPosition(WORLD_WIDTH * 0.25f, WORLD_HEIGHT * 0.25f));
        scene.addThingSpec("emitter", InstanceParams.New().setPosition(WORLD_WIDTH * 0.75f, WORLD_HEIGHT * 0.75f));

        JJ.things.loadNow(scene);
    }

    private ThingSpec emitter() {
        return new ThingSpec().addBehaviour(new Emitter().setIntervalSeconds(0.05f).setSpecPath("square"));
    }


    private ThingSpec square() {
        DestroySelf destroySelf = new DestroySelf();
        destroySelf.timeToLive.set(4f);
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        RenderBuilder.b2d().setColor(Color.BLUE).build()
                                )
                                .addPhysics(new FixtureNodeSpec().setShape(new B2DShapeSpec.PolygonRectangle(5, 5)))
                )
                .addBehaviour(destroySelf)
                ;
    }
}
