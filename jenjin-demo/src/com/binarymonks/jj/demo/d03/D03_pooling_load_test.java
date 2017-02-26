package com.binarymonks.jj.demo.d03;

import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.components.DestroySelf;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.specs.SceneSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.b2d.FixtureNodeSpec;
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

        JJ.specs
                .set("square", square())
                .set("emitter", emitter())
        ;

        SceneSpec scene = new SceneSpec();
        scene.addThingSpec("emitter", InstanceParams.New().setPosition(WORLD_WIDTH * 0.25f, WORLD_HEIGHT * 0.25f));
        scene.addThingSpec("emitter", InstanceParams.New().setPosition(WORLD_WIDTH * 0.75f, WORLD_HEIGHT * 0.75f));

        JJ.things.loadNow(scene);
    }

    private ThingSpec emitter() {
        return new ThingSpec().addComponent(new Emitter().setIntervalSeconds(0.05f).setSpecPath("square"));
    }


    private ThingSpec square() {
        DestroySelf destroySelf = new DestroySelf();
        destroySelf.timeToLive.set(4f);
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .setRender(
                                        RenderBuilder.b2d().setColor(Color.BLUE).build()
                                )
                                .setPhysics(new FixtureNodeSpec().setShape(new B2DShapeSpec.PolygonRectangle(5, 5)))
                )
                .addComponent(destroySelf)
                ;
    }
}
