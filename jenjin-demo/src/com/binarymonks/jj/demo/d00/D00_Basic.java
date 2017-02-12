package com.binarymonks.jj.demo.d00;

import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.render.specs.ShapeRenderSpec;
import com.binarymonks.jj.render.specs.Spatial;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.SceneSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

/**
 * The simplest of the simple - just draw something.
 */
public class D00_Basic extends Game {

    public D00_Basic(com.binarymonks.jj.JJConfig JJConfig) {
        super(JJConfig);
    }

    @Override
    protected void gameOn() {


        GameRenderingLayer gameRenderingLayer = new GameRenderingLayer(100, 0, 0);
        JJ.layers.addLayerTop(gameRenderingLayer);

        JJ.specs
                .set("squares", squares());

        //Add instances to a level
        SceneSpec level = new SceneSpec()
                .add("squares",
                        InstanceParams.New().setPosition(10, 0).setRotationD(90),
                        InstanceParams.New().setPosition(-10, 0).setRotationD(90)
                );

        //Load the level asynchronously with a callback when it is done
        JJ.things.load(level, this::onLevelLoaded);
    }


    private void onLevelLoaded() {
        //Get the player instance and hook in controls
        Thing player = JJ.things.getThingByName("PlayerThing");

        //Hide the splashscreen


    }


    private ThingSpec squares() {
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        new ShapeRenderSpec.Rectangle()
                                                .setLayer(0)
                                                .setFill(true)
                                                .setDimension(10, 10)
                                                .setSpatial(new Spatial.Fixed().setOffset(5,5).setRotationD(45))
                                                .color.set(Color.BLUE)
                                )
                                .addPhysics(
                                        new FixtureNodeSpec()
                                                .setShape(new B2DShapeSpec.PolygonRectangle(10, 10))
                                                .setOffset(5, 5)
                                                .setRotationD(45)
                                )
                )
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        new ShapeRenderSpec.Rectangle()
                                                .setLayer(0)
                                                .setFill(true)
                                                .setDimension(10, 10)
                                                .setSpatial(new Spatial.DelegateToPhysics())
                                                .color.set(Color.RED)
                                )
                                .addPhysics(
                                        new FixtureNodeSpec()
                                                .setShape(new B2DShapeSpec.PolygonRectangle(10, 10))
                                                .setOffset(-20, 5)
                                                .setRotationD(45)
                                )
                )
                ;
    }
}
