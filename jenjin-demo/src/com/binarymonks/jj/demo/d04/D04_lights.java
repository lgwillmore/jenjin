package com.binarymonks.jj.demo.d04;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.components.VelocityTouchable;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.specs.lights.LightSpec;
import com.binarymonks.jj.physics.CollisionGroups;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;
import com.binarymonks.jj.specs.SceneSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.b2d.FixtureNodeSpec;
import com.binarymonks.jj.specs.render.BackingTexture;
import com.binarymonks.jj.specs.render.RenderBuilder;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.specs.ThingNodeSpec;
import com.binarymonks.jj.specs.ThingSpec;

public class D04_lights extends Game {

    float WORLD_WIDTH = 100;
    float WORLD_HEIGHT = 100;

    public D04_lights(JJConfig jjconfig) {
        super(jjconfig);
    }

    GameRenderingLayer gameRenderingLayer;

    @Override
    protected void gameOn() {
        gameRenderingLayer = JJ.layers.getDefaultGameLayer();

        JJ.specs
                .set("background", background())
                .set("block", block())
                .set("light", light())
        ;

        SceneSpec scene = new SceneSpec();
        scene.addThingSpec("background", InstanceParams.New().setPosition(WORLD_WIDTH * 0.5f, WORLD_HEIGHT * 0.5f).setRotationD(90));
        scene.addThingSpec("block", InstanceParams.New().setPosition(30, 30));
        scene.addThingSpec("block", InstanceParams.New().setPosition(60, 60));
        scene.addThingSpec("block", InstanceParams.New().setPosition(90, 90));
        scene.addThingSpec("light", InstanceParams.New().setUniqueName("light").setPosition(90, 40));

        JJ.lights.setAmbientLight(0, 0, 0, 0.3f);

        JJ.things.loadNow(scene);
    }


    private ThingSpec background() {
        float width = 1536f;
        float height = 2048f;
        float heightToWidth = height / width;
        return new ThingSpec()
                .addNode(
                        new ThingNodeSpec()
                                .setRender(
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
                .setShape(new B2DShapeSpec.PolygonRectangle(5, 5));
        fixtureNodeSpec
                .collisionData.setToExplicit(CollisionGroups.EVERYTHING);
        return new ThingSpec()
                .addNode(
                        new ThingNodeSpec()
                                .setRender(RenderBuilder.b2d()
                                        .setLayer(1)
                                        .setColor(Color.BLUE)
                                        .build()
                                )
                                .setPhysics(fixtureNodeSpec)
                );
    }

    private ThingSpec light() {
        LightSpec.Point point = new LightSpec.Point();
        point.color.set(Color.RED);
        point.setReach(90);
        point.setRays(200);
        point.collisionData.setToExplicit(CollisionGroups.EVERYTHING);
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.KinematicBody))
                .addLight(point)
                .addNode(new ThingNodeSpec()
                        .setRender(RenderBuilder.b2d()
                                .setColor(new Color(1f, 0.8f, 0.8f, 1))
                                .setGraphToLightSource().build()
                        )
                        .setPhysics(new FixtureNodeSpec().setShape(new B2DShapeSpec.PolygonRectangle(5, 5)))
                )
                .addComponent(new VelocityTouchable())
                ;
    }


}
