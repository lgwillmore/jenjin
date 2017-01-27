package com.binarymonks.jj.demo.d01;

import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.layers.DefaultLayer;
import com.binarymonks.jj.physics.specs.ShapePhysicsSpec;
import com.binarymonks.jj.render.specs.RenderSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.SceneSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

public class D01_full_simple_game extends Game {
    @Override
    protected void gameOn() {

        //Show a splash screen
        //JJ.render.layers.addLayerTop(new DefaultLayer());

        //Load ThingSpecs
        JJ.things.specs
                .set("enemy/1", enemeySpec())
                .set("player", playerSpec());

        //Add instances to a level
        SceneSpec level = new SceneSpec()
                .add("enemy/1",
                        InstanceParams.New(),
                        InstanceParams.New(),
                        InstanceParams.New()
                )
                .add("player", InstanceParams.New());

        //Load the level asynchronously with a callback when it is done
        JJ.things.world.load(level, this::onLevelLoaded);
    }


    private void onLevelLoaded() {
        //Get the player instance and hook in controls
        Thing player = JJ.things.world.getThingByName("PlayerThing");

        //Hide the splashscreen


    }


    private ThingSpec enemeySpec() {
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        new RenderSpec.Null())
                                .addPhysics(
                                        new ShapePhysicsSpec()
                                )
                ).addNode(
                        new NodeSpec()
                                .addRender(
                                        new RenderSpec.Null()
                                )
                );
    }

    private ThingSpec playerSpec() {
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        new RenderSpec.Null()
                                )
                ).addNode(
                        new NodeSpec()
                                .addRender(
                                        new RenderSpec.Null()
                                )
                );
    }
}
