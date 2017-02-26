package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.specs.SceneSpec;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.b2d.FixtureNodeSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.SceneParams;

public class D08_arrow_game extends Game {

    public static float WORLD_WIDTH = 60;
    public static float WORLD_HEIGHT = 30;

    public D08_arrow_game(JJConfig jjconfig) {
        super(jjconfig);
    }

    @Override
    protected void gameOn() {
        JJ.layers.getDefaultGameLayer().setView(WORLD_WIDTH, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        Global.physics.world.setGravity(new Vector2(0, -9.8f));

        JJ.specs.set("bow", bow());
        JJ.specs.set("arrow", arrow());
        JJ.specs.set("quiver", quiver());
        JJ.specs.set("dummy/head", dummyHead());
        JJ.specs.set("dummy/body", dummyBody());

        SceneSpec scene = new SceneSpec();
        scene.addThingSpec("bow", InstanceParams.New().setPosition(WORLD_WIDTH * 0.3f, WORLD_HEIGHT * 0.5f));
        scene.addThingSpec("quiver", InstanceParams.New().setPosition(WORLD_WIDTH * 0.1f, WORLD_HEIGHT * 0.75f));

        JJ.things.loadNow(scene);

        SceneSpec dummyScene = dummy();

        JJ.things.appendScene(scene, SceneParams.New().setPosition(WORLD_WIDTH*0.7f,WORLD_HEIGHT * 0.5f));

    }


    public ThingSpec bow() {
        ThingSpec spec = new ThingSpec();

        spec.setPhysics(new PhysicsRootSpec.B2D().setGravityScale(0));

        spec.newNode()
                .setPhysics(
                        new FixtureNodeSpec()
                                .setShape(new B2DShapeSpec.Circle(1.5f))
                                .setSensor(true)
                                .addInitialBeginCollision(new BowNotchCollision())
                );
        spec.addComponent(new Bow());
        return spec;
    }

    public ThingSpec arrow() {
        ThingSpec spec = new ThingSpec();

        spec.setPhysics(new PhysicsRootSpec.B2D().setBullet(true));

        spec.newNode()
                .setPhysics(
                        new FixtureNodeSpec()
                                .setShape(new B2DShapeSpec.PolygonRectangle(2,0.1f))
                );
        spec.newNode()
                .setPhysics(
                  new FixtureNodeSpec()
                        .setShape(new B2DShapeSpec.Circle(0.15f))
                        .setOffset(0.85f,0)
                );
        spec.addComponent(new Arrow());
        return spec;
    }

    public ThingSpec quiver() {
        ThingSpec spec = new ThingSpec();

        spec.setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.StaticBody));

        spec.newNode()
                .setPhysics(
                        new FixtureNodeSpec()
                                .setShape(new B2DShapeSpec.PolygonRectangle(1, 2))
                        .setSensor(true)
                );
        spec.addComponent(new QuiverTouch());
        return spec;
    }

    public SceneSpec dummy(){
        SceneSpec scene = new SceneSpec();
        int head = scene.addThingSpec("dummy/head",InstanceParams.New().setPosition(0,2.15f));
        int body = scene.addThingSpec("dummy/body",InstanceParams.New().setPosition(0,1f));

        WeldJointDef wj = new WeldJointDef();
        wj.localAnchorA.set(0,1.15f);
        wj.localAnchorB.set(0,0);
        scene.addJoint(body,head,wj);

        return scene;
    }

    public ThingSpec dummyHead(){
        ThingSpec spec = new ThingSpec();

        spec.setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.DynamicBody));

        spec.newNode()
                .setPhysics(
                        new FixtureNodeSpec()
                                .setShape(new B2DShapeSpec.Circle(0.15f))
                );
        return spec;
    }

    public ThingSpec dummyBody(){
        ThingSpec spec = new ThingSpec();

        spec.setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.DynamicBody));

        spec.newNode()
                .setPhysics(
                        new FixtureNodeSpec()
                                .setShape(new B2DShapeSpec.PolygonRectangle(0.5f,2f))
                );
        return spec;
    }

}
