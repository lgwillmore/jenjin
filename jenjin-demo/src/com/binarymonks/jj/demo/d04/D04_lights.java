package com.binarymonks.jj.demo.d04;

import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.render.specs.BackingTexture;
import com.binarymonks.jj.render.specs.Spatial;
import com.binarymonks.jj.render.specs.TextureRenderSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.SceneSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

public class D04_lights extends Game {

    float WORLD_WIDTH = 1500;
    float WORLD_HEIGHT = 1500;

    public D04_lights(JJConfig jjconfig) {
        super(jjconfig);
    }

    @Override
    protected void gameOn() {
        GameRenderingLayer gameRenderingLayer = new GameRenderingLayer(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        JJ.layers.addLayerTop(gameRenderingLayer);

        JJ.specs
                .set("background", background())
        ;

        SceneSpec scene = new SceneSpec();
        scene.add("background",
                InstanceParams.New().setPosition(WORLD_WIDTH * 0.5f, WORLD_HEIGHT * 0.5f).setRotationD(90)
        );

        JJ.things.loadNow(scene);
    }

    private ThingSpec background() {
        float width=1536f;
        float height = 2048f;
        float heightToWidth = height / width;
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        new TextureRenderSpec(
                                                new BackingTexture.SimpleTexture("textures/circuit_background.png"),
                                                WORLD_WIDTH,
                                                WORLD_WIDTH * heightToWidth
                                        )
                                )
                );
    }


}
