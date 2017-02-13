package com.binarymonks.jj.demo.d05;

import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.render.specs.AnimatedRenderSpec;
import com.binarymonks.jj.render.specs.AnimationSequence;
import com.binarymonks.jj.render.specs.BackingTexture;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.SceneSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

public class D05_animations extends Game {
    float WORLD_WIDTH = 1500;
    float WORLD_HEIGHT = 1500;

    public D05_animations(JJConfig jjconfig) {
        super(jjconfig);
    }

    GameRenderingLayer gameRenderingLayer;

    @Override
    protected void gameOn() {
        gameRenderingLayer = new GameRenderingLayer(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        JJ.layers.addLayerTop(gameRenderingLayer);

        JJ.specs
                .set("twinkle_animated", twinkleAnimated())
        ;

        SceneSpec scene = new SceneSpec();
        scene.add("twinkle_animated",
                InstanceParams.New().setPosition(WORLD_WIDTH * 0.5f, WORLD_HEIGHT * 0.5f)
        )
        ;

        JJ.lights.setAmbientLight(0, 0, 0, 0.3f);

        JJ.things.load(scene, this::gameLoaded);
    }

    private void gameLoaded() {

    }

    private ThingSpec twinkleAnimated() {
        return new ThingSpec()
                .addNode(new NodeSpec()
                        .addRender(
                                new AnimatedRenderSpec()
                                        .setBackingTexture(new BackingTexture.Simple("textures/twinkle_animation.png"))
                                        .setRowsNColumns(2, 3)
                                        .addAnimation(new AnimationSequence()
                                                .setDuration(0.5f)
                                                .setName("default")
                                                .setStartEnd(0, 5)
                                        )
                        )

                );
    }
}
