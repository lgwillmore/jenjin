package com.binarymonks.jj.demo.d01;

import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.render.specs.RenderSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.SceneSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

public class D01_full_simple_game extends Game {

    public D01_full_simple_game(com.binarymonks.jj.JJConfig JJConfig) {
        super(JJConfig);
    }

    @Override
    protected void gameOn() {

        //Show a splash screen
        GameRenderingLayer gameRenderingLayer = new GameRenderingLayer(100, 0, 0);
        JJ.layers.addLayerTop(gameRenderingLayer);
        //JJ.layers.addLayerTop(new DefaultLayer());

        //Load ThingSpecs
        JJ.specs
                .set("enemy/1", enemeySpec());

        //Add instances to a level
        SceneSpec level = new SceneSpec()
                .add("enemy/1",
                        InstanceParams.New().setPosition(0, 20),
                        InstanceParams.New().setPosition(0, -20)
                );

        //Load the level asynchronously with a callback when it is done
        JJ.things.load(level, this::onLevelLoaded);
    }


    private void onLevelLoaded() {
        //Get the player instance and hook in controls
        Thing player = JJ.things.getThingByName("PlayerThing");

        //Hide the splashscreen


    }


    private ThingSpec enemeySpec() {
        RenderSpec.Shape.Rect renderSpec = new RenderSpec.Shape.Rect();
        renderSpec.draw.setColor(Color.BLUE);
        renderSpec.draw.setFill(true);
        renderSpec.order.setLayer(1);
        renderSpec.order.setPriority(0);
        renderSpec.spatial.setX(5);
        renderSpec.spatial.setY(5);
        renderSpec.dimension.setWidth(10);
        renderSpec.dimension.setHeight(10);

        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        renderSpec
                                )
                                .addPhysics(
                                        new FixtureNodeSpec()
                                )
                );
    }
}
