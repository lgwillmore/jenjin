package com.binarymonks.jj.demo.d01;

import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.render.specs.ShapeRenderSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.SceneSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

public class D01_pong extends Game {

    public static float COURT_LENGTH = 100;
    public static float BAT_HEIGHT = 20;
    public static float BAT_WIDTH = 5;

    public D01_pong(com.binarymonks.jj.JJConfig JJConfig) {
        super(JJConfig);
    }

    @Override
    protected void gameOn() {
        GameRenderingLayer gameRenderingLayer = new GameRenderingLayer(COURT_LENGTH, COURT_LENGTH / 2, COURT_LENGTH / 2);
        JJ.layers.addLayerTop(gameRenderingLayer);

        //Define your ThingSpecs
        JJ.specs
                .set("bot", bot())
                .set("player", player());


        //Add instances of your ThingSpecs to a scene
        SceneSpec level = new SceneSpec()
                .add("bot",
                        InstanceParams.New().setPosition(COURT_LENGTH - 10, COURT_LENGTH / 2).setRotationD(0)
                )
                .add("player",
                        InstanceParams.New().setUniqueName("player_bat").setPosition(10, COURT_LENGTH / 2)
                );

        //Load the the scene
        JJ.things.load(level, this::onLevelLoad);
    }

    private void onLevelLoad(){
        Thing player = JJ.things.getThingByName("player_bat");
        System.out.println(player.uniqueName);
    }


    private ThingSpec bot() {
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        new ShapeRenderSpec.Rectangle()
                                                .setDimension(BAT_WIDTH, BAT_HEIGHT)
                                                .setColor(Color.WHITE)
                                )
                                .addPhysics(
                                        new FixtureNodeSpec()
                                                .setShape(new B2DShapeSpec.PolygonSquare(BAT_WIDTH, BAT_HEIGHT))
                                )
                )
                .addBehaviour(
                        new RandomBotBehaviour()
                )
                ;
    }

    private ThingSpec player() {
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        new ShapeRenderSpec.Rectangle()
                                                .setDimension(BAT_WIDTH, BAT_HEIGHT)
                                                .setColor(Color.WHITE)
                                )
                                .addPhysics(
                                        new FixtureNodeSpec()
                                                .setShape(new B2DShapeSpec.PolygonSquare(BAT_WIDTH, BAT_HEIGHT))
                                )
                )
                ;
    }
}
