package com.binarymonks.jj.playground;

import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.layers.DefaultLayer;
import com.binarymonks.jj.objects.specs.NodeSpec;
import com.binarymonks.jj.objects.specs.RootSpec;
import com.binarymonks.jj.objects.specs.SceneSpec;
import com.binarymonks.jj.physics.specs.ShapePhysicsSpec;
import com.binarymonks.jj.render.specs.RenderSpec;


public class PlayGround extends Game {
    @Override
    protected void gameOn() {
        //Show a splash screen
        JJ.render.layers.addLayerTop(new DefaultLayer());

        //Get the level specification
        SceneSpec level = new SceneSpec()
                .add(enemeySpec())
                .add(playerSpec());

        //Load the level asynchronously with a callback when it is done
        JJ.world.load(level, this::onLevelLoaded);
    }

    private void onLevelLoaded() {

    }


    private RootSpec enemeySpec() {
        return new RootSpec()
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

    private RootSpec playerSpec() {
        return new RootSpec()
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
