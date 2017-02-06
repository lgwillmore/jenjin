package com.binarymonks.jj.demo.d02;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.physics.specs.PhysicsRootSpec;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.render.specs.B2DRenderSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.SceneSpec;
import com.binarymonks.jj.things.specs.ThingSpec;


public class D02_b2d_shapes extends Game {
    float WORLD_WIDTH = 100;
    float WORLD_HEIGHT = 100;

    public D02_b2d_shapes(JJConfig jjconfig) {
        super(jjconfig);
    }

    @Override
    protected void gameOn() {
        GameRenderingLayer gameRenderingLayer = new GameRenderingLayer(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        gameRenderingLayer.setDebug(true);
        JJ.layers.addLayerTop(gameRenderingLayer);

        JJ.specs.set("rectangle", rectangle());

        SceneSpec scene = new SceneSpec();
        scene.add("rectangle",
                InstanceParams.New().setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2)
        );

        JJ.things.loadNow(scene);
    }

    private ThingSpec rectangle() {
        /**
         * Render layers are rendered by layer and then priority.
         */
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.StaticBody))
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .setColor(Color.YELLOW)
                                        .setLayer(0)
                                        .setPriority(1)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(20, 20)))
                )
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .setColor(Color.GREEN)
                                        .setLayer(1)
                                        .setPriority(0)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(5, 5)))
                )
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .setColor(Color.BLUE)
                                        .setLayer(0)
                                        .setPriority(0)
                                        .setOffset(10,10)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(5, 5)))
                )
                ;
    }
}
