package com.binarymonks.jj.demo.d02;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.physics.specs.PhysicsRootSpec;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.render.specs.B2DRenderSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
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
        JJ.layers.addLayerTop(gameRenderingLayer);
        Global.physics.world.setGravity(new Vector2(0, -10));

        JJ.specs
                .set("multi", multi())
                .set("polygon", polygon())
                .set("rectangle", rectangle())
                .set("ball", ball())
        ;

        SceneSpec scene = new SceneSpec();
        scene
                .add("multi",
                        InstanceParams.New().setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2).setRotationD(45)
                )
                .add("polygon",
                        InstanceParams.New().setPosition(70, 70))
                .add("rectangle",
                        InstanceParams.New().setPosition(WORLD_WIDTH / 2, 0),
                        InstanceParams.New().setPosition(0, WORLD_WIDTH / 2).setRotationD(90),
                        InstanceParams.New().setPosition(WORLD_WIDTH, WORLD_WIDTH / 2).setRotationD(90)
                )
                .add("ball",
                        InstanceParams.New().setPosition(20, WORLD_HEIGHT),
                        InstanceParams.New().setPosition(40, WORLD_HEIGHT),
                        InstanceParams.New().setPosition(60, WORLD_HEIGHT),
                        InstanceParams.New().setPosition(80, WORLD_HEIGHT))
        ;


        JJ.things.loadNow(scene);
    }

    private ThingSpec ball() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.DynamicBody))
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .color.set(Color.GRAY)
                                        .setLayer(0)
                                        .setPriority(1)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.Circle(5))
                                        .setRestitution(0.6f)
                                        .setDensity(0.2f)
                                        .setOffset(10, 10)
                                )
                );
    }

    private ThingSpec rectangle() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.StaticBody))
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .color.set(Color.GRAY)
                                        .setLayer(0)
                                        .setPriority(1)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(WORLD_WIDTH, 10)))
                );
    }

    private ThingSpec polygon() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.DynamicBody))
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec().color.set(Color.PINK))
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.Polygon()
                                                .add(N.ew(Vector2.class).set(-10, -10))
                                                .add(N.ew(Vector2.class).set(-15, 0))
                                                .add(N.ew(Vector2.class).set(-10, 10))
                                                .add(N.ew(Vector2.class).set(0, 15))
                                                .add(N.ew(Vector2.class).set(10, 10))
                                                .add(N.ew(Vector2.class).set(15, 0))
                                                .add(N.ew(Vector2.class).set(10, -10))
                                                .add(N.ew(Vector2.class).set(0, -15))
                                        )
                                )
                )
                ;
    }

    private ThingSpec multi() {
        /**
         * Render layers are rendered by layer and then priority.
         */
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.DynamicBody))
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .color.set(Color.YELLOW)
                                        .setLayer(0)
                                        .setPriority(1)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(20, 20)))
                )
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .color.set(Color.GREEN)
                                        .setLayer(1)
                                        .setPriority(0)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(5, 5)))
                )
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .color.set(Color.BLUE)
                                        .setLayer(0)
                                        .setPriority(0)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(5, 5))
                                        .setOffset(-10, 10)
                                )
                )
                ;
    }
}
