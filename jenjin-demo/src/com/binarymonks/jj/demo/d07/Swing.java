package com.binarymonks.jj.demo.d07;

import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.specs.SceneSpec;
import com.binarymonks.jj.things.InstanceParams;

public class Swing extends Game {

    GameRenderingLayer gameRenderingLayer;
    float WORLD_WIDTH = 100;
    float WORLD_HEIGHT = 100;

    public Swing(JJConfig jjconfig) {
        super(jjconfig);
    }

    @Override
    protected void gameOn() {
        gameRenderingLayer = new GameRenderingLayer(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        JJ.layers.addLayerTop(gameRenderingLayer);

        JJ.specs.set("player", GameSpecs.player());

        SceneSpec scene = new SceneSpec();
        scene.addThingSpec("player", InstanceParams.New().setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2).setUniqueName("player"));


        JJ.things.load(scene, this::onLoad);

    }

    private void onLoad() {
//        ImpulseMovement playerMouse = JJ.things.getThingByName("player").getComponent(ImpulseMovement.class);
//        JJ.input.mapTouch(0,playerMouse::setTarget);
    }
}
