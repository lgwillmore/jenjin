package com.binarymonks.jj.demo.d04;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.lights.specs.LightSpec;
import com.binarymonks.jj.physics.CollisionGroups;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;
import com.binarymonks.jj.specs.B2DCompositeSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.FixtureNodeSpec;
import com.binarymonks.jj.specs.render.B2DRenderSpec;
import com.binarymonks.jj.specs.render.BackingTexture;
import com.binarymonks.jj.specs.render.RenderBuilder;
import com.binarymonks.jj.specs.render.TextureRenderSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.specs.NodeSpec;
import com.binarymonks.jj.specs.ThingSpec;

public class D04_lights extends Game {

    float WORLD_WIDTH = 1500;
    float WORLD_HEIGHT = 1500;

    public D04_lights(JJConfig jjconfig) {
        super(jjconfig);
    }

    GameRenderingLayer gameRenderingLayer;

    @Override
    protected void gameOn() {
        gameRenderingLayer = new GameRenderingLayer(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        JJ.layers.addLayerTop(gameRenderingLayer);

        JJ.specs
                .set("background", background())
                .set("block", block())
                .set("light", light())
        ;

        B2DCompositeSpec scene = new B2DCompositeSpec();
        scene.addThingSpec("background", InstanceParams.New().setPosition(WORLD_WIDTH * 0.5f, WORLD_HEIGHT * 0.5f).setRotationD(90));
        scene.addThingSpec("block", InstanceParams.New().setPosition(300, 300));
        scene.addThingSpec("block", InstanceParams.New().setPosition(700, 700));
        scene.addThingSpec("block", InstanceParams.New().setPosition(1100, 1100));
        scene.addThingSpec("light", InstanceParams.New().setUniqueName("light").setPosition(1000, 400));

        JJ.lights.setAmbientLight(0, 0, 0, 0.3f);

        JJ.things.load(scene, this::onLoad);
    }

    public void onLoad() {
        Thing light = JJ.things.getThingByName("light");
        gameRenderingLayer.addMouseDrag(light);
    }

    private ThingSpec background() {
        float width = 1536f;
        float height = 2048f;
        float heightToWidth = height / width;
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(
                                        RenderBuilder.texture(
                                                new BackingTexture.Simple("textures/circuit_background.png"),
                                                WORLD_WIDTH,
                                                WORLD_WIDTH * heightToWidth
                                        )
                                                .setLayer(0).build()
                                )
                );
    }

    private ThingSpec block() {
        FixtureNodeSpec fixtureNodeSpec = new FixtureNodeSpec()
                .setShape(new B2DShapeSpec.PolygonRectangle(100, 100));
        fixtureNodeSpec
                .collisionData.setToExplicit(CollisionGroups.EVERYTHING);
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(RenderBuilder.b2d()
                                        .setLayer(1)
                                        .setColor(Color.BLUE)
                                        .build()
                                )
                                .addPhysics(fixtureNodeSpec)
                );
    }

    private ThingSpec light() {
        LightSpec.Point point = new LightSpec.Point();
        point.color.set(Color.RED);
        point.setReach(1500);
        point.setRays(200);
        point.collisionData.setToExplicit(CollisionGroups.EVERYTHING);
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.KinematicBody))
                .addLight(point)
                .addNode(new NodeSpec()
                        .addRender(RenderBuilder.b2d()
                                .setColor(new Color(1f, 0.8f, 0.8f, 1))
                                .setGraphToLightSource().build()
                        )
                        .addPhysics(new FixtureNodeSpec().setShape(new B2DShapeSpec.PolygonRectangle(50, 50)))
                )
                ;
    }


}
