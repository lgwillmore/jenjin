package com.binarymonks.jj.demo.d05;

import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.specs.SceneSpec;
import com.binarymonks.jj.specs.render.AnimationSequence;
import com.binarymonks.jj.specs.render.BackingTexture;
import com.binarymonks.jj.specs.render.RenderBuilder;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.specs.NodeSpec;
import com.binarymonks.jj.specs.ThingSpec;

public class D05_animations extends Game {
    float WORLD_WIDTH = 200;
    float WORLD_HEIGHT = 200;

    public D05_animations(JJConfig jjconfig) {
        super(jjconfig);
    }

    GameRenderingLayer gameRenderingLayer;

    @Override
    protected void gameOn() {
        gameRenderingLayer = new GameRenderingLayer(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        JJ.layers.addLayerTop(gameRenderingLayer);

        JJ.specs.set("twinkle_animated", twinkleAnimated());

        SceneSpec scene = new SceneSpec();
        scene.addThingSpec("twinkle_animated", InstanceParams.New().setPosition(WORLD_WIDTH * 0.5f, WORLD_HEIGHT * 0.5f));


        JJ.things.load(scene, this::gameLoaded);
    }

    private void gameLoaded() {

    }

    private ThingSpec twinkleAnimated() {
        return new ThingSpec()
                .addNode(new NodeSpec()
                        .addRender(
                                RenderBuilder.animated(new BackingTexture.Simple("textures/count.png"),
                                        3, 4,
                                        100, 100)
                                        .addAnimation(new AnimationSequence()
                                                .setDuration(6f)
                                                .setName("default")
                                                .setStartEnd(4, 8)
                                        ).build()
                        )

                );
    }
}
