package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
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

public class D08_arrow_game extends Game {

    public static float WORLD_WIDTH = 30;
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

        SceneSpec scene = new SceneSpec();
        scene.addThingSpec("bow", InstanceParams.New().setPosition(WORLD_WIDTH * 0.3f, WORLD_HEIGHT * 0.5f));
        scene.addThingSpec("quiver", InstanceParams.New().setPosition(WORLD_WIDTH * 0.1f, WORLD_HEIGHT * 0.75f));

        JJ.things.loadNow(scene);

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

        spec.setPhysics(new PhysicsRootSpec.B2D());

        spec.newNode()
                .setPhysics(
                        new FixtureNodeSpec()
                                .setShape(new B2DShapeSpec.Circle(0.25f))
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
                );
        spec.addComponent(new QuiverTouch());
        return spec;
    }

}
