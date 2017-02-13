package com.binarymonks.jj.demo.d04;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.lights.specs.LightSpec;
import com.binarymonks.jj.physics.CollisionGroups;
import com.binarymonks.jj.physics.specs.PhysicsRootSpec;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.render.specs.B2DRenderSpec;
import com.binarymonks.jj.render.specs.BackingTexture;
import com.binarymonks.jj.render.specs.TextureRenderSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.SceneSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

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

        SceneSpec scene = new SceneSpec();
        scene.add("background",
                InstanceParams.New().setPosition(WORLD_WIDTH * 0.5f, WORLD_HEIGHT * 0.5f).setRotationD(90)
        ).add("block",
                InstanceParams.New().setPosition(300, 300),
                InstanceParams.New().setPosition(700, 700),
                InstanceParams.New().setPosition(1100, 1100)
        ).add("light",
                InstanceParams.New().setUniqueName("light").setPosition(1000, 400))
        ;

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
                                        new TextureRenderSpec(
                                                new BackingTexture.Simple("textures/circuit_background.png"),
                                                WORLD_WIDTH,
                                                WORLD_WIDTH * heightToWidth
                                        )
                                                .setLayer(0)
                                )
                );
    }

    private ThingSpec block() {
        return new ThingSpec()
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .setLayer(1)
                                        .color.set(Color.BLUE)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(100, 100))
                                        .collisionData.setToExplicit(CollisionGroups.EVERYTHING)
                                )
                );
    }

    private ThingSpec light() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.KinematicBody))
                .addLight(new LightSpec.Point()
                        .color.set(Color.RED)
                        .setReach(1500)
                        .setRays(200)
                        .collisionData.setToExplicit(CollisionGroups.EVERYTHING)
                )
                .addNode(new NodeSpec()
                        .addRender(new B2DRenderSpec()
                                .color.set(new Color(1f, 0.8f, 0.8f, 1))
                                .renderGraph.setToLightSource()
                        )
                        .addPhysics(new FixtureNodeSpec().setShape(new B2DShapeSpec.PolygonRectangle(50, 50)))
                )
                ;
    }


}
