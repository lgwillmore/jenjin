package com.binarymonks.jj.demo.d02;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.physics.specs.PhysicsRootSpec;
import com.binarymonks.jj.physics.specs.b2d.B2DCompositeSpec;
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
    float WORLD_WIDTH = 200;
    float WORLD_HEIGHT = 200;

    public D02_b2d_shapes(JJConfig jjconfig) {
        super(jjconfig);
    }

    @Override
    protected void gameOn() {
        GameRenderingLayer gameRenderingLayer = new GameRenderingLayer(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        JJ.layers.addLayerTop(gameRenderingLayer);
        Global.physics.world.setGravity(new Vector2(0, -20));

        JJ.specs
                .set("multi", multi())
                .set("polygon", polygon())
                .set("rectangle", rectangle())
                .set("ball", ball())
                .set("chain", chain())
        ;

        B2DCompositeSpec scene = buildScene();


        JJ.things.loadNow(scene);
    }

    private B2DCompositeSpec buildScene() {
        B2DCompositeSpec scene = new B2DCompositeSpec();
        scene.addThingSpec("multi",
                InstanceParams.New().setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2).setRotationD(45)
        );
        scene.addThingSpec("polygon",
                InstanceParams.New().setPosition(70, 70));
        scene.addThingSpec("rectangle", InstanceParams.New().setPosition(WORLD_WIDTH / 2, 0).setScale(WORLD_WIDTH, 10));
        scene.addThingSpec("rectangle", InstanceParams.New().setPosition(0, WORLD_WIDTH / 2).setRotationD(90).setScale(WORLD_WIDTH, 10));
        scene.addThingSpec("rectangle", InstanceParams.New().setPosition(WORLD_WIDTH, WORLD_WIDTH / 2).setRotationD(90).setScale(WORLD_WIDTH, 10));
        int shelf = scene.addThingSpec("rectangle", InstanceParams.New().setPosition(150, 150 / 2).setScale(20, 10));
        float lastBallY = 145;
        int lastBall = scene.addThingSpec("ball", InstanceParams.New().setPosition(lastBallY, 150).setRotationD(45));
        RevoluteJointDef revJoint = new RevoluteJointDef();
        revJoint.localAnchorA.set(0, 0);
        revJoint.localAnchorB.set(0, 5);
        revJoint.enableLimit = false;
        revJoint.collideConnected=false;
        scene.addJoint(shelf, lastBall, revJoint);
        for (int i = 0; i < 7; i++) {
            int newBall = scene.addThingSpec("ball", InstanceParams.New().setPosition(lastBallY, 150).setRotationD(45));
            revJoint = new RevoluteJointDef();
            revJoint.localAnchorA.set(0, -5);
            revJoint.localAnchorB.set(0, 5);
            revJoint.enableLimit = false;
            revJoint.collideConnected=false;
            scene.addJoint(lastBall, newBall, revJoint);
            lastBallY += 5;
            lastBall = newBall;
        }

        scene.addThingSpec("ball", InstanceParams.New().setPosition(20, WORLD_HEIGHT));
        scene.addThingSpec("ball", InstanceParams.New().setPosition(40, WORLD_HEIGHT));
        scene.addThingSpec("ball", InstanceParams.New().setPosition(60, WORLD_HEIGHT));
        scene.addThingSpec("ball", InstanceParams.New().setPosition(80, WORLD_HEIGHT));
        scene.addThingSpec("chain", InstanceParams.New().setPosition(60, 15));
        return scene;
    }

    private ThingSpec chain() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.StaticBody))
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .color.set(Color.PURPLE)
                                        .setLayer(0)
                                        .setPriority(1)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.Chain()
                                                .add(N.ew(Vector2.class).set(-10, -10))
                                                .add(N.ew(Vector2.class).set(-5, -10))
                                                .add(N.ew(Vector2.class).set(-5, -5))
                                                .add(N.ew(Vector2.class).set(0, -5))
                                                .add(N.ew(Vector2.class).set(0, 0))
                                                .add(N.ew(Vector2.class).set(5, 0))
                                                .add(N.ew(Vector2.class).set(5, 5))
                                                .add(N.ew(Vector2.class).set(10, 5))
                                                .add(N.ew(Vector2.class).set(10, 10))
                                        )
                                        .setRestitution(0.0f)
                                        .setDensity(0.5f)
                                )
                );
    }

    private ThingSpec ball() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.DynamicBody))
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .color.set(Color.FIREBRICK)
                                        .setLayer(0)
                                        .setPriority(1)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.Circle(5))
                                        .setRestitution(0.6f)
                                        .setDensity(0.2f)
                                )
                );
    }

    private ThingSpec rectangle() {
        return new ThingSpec()
                .setPool(false)
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.StaticBody))
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec()
                                        .color.set(Color.GRAY)
                                        .setLayer(0)
                                        .setPriority(1)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(1, 1)))
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
