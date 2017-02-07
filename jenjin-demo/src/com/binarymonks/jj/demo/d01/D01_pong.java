package com.binarymonks.jj.demo.d01;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.audio.SoundParams;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.physics.collisions.EmitEventCollision;
import com.binarymonks.jj.physics.collisions.SoundCollision;
import com.binarymonks.jj.physics.specs.PhysicsRootSpec;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.render.specs.B2DRenderSpec;
import com.binarymonks.jj.render.specs.ShapeRenderSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.SceneSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

public class D01_pong extends Game {

    public static float COURT_LENGTH = 100;
    public static float BAT_HEIGHT = 20;
    public static float BAT_WIDTH = 5;
    public static float BAT_INSET = 10;

    public D01_pong(com.binarymonks.jj.JJConfig JJConfig) {
        super(JJConfig);
    }

    @Override
    protected void gameOn() {
        GameRenderingLayer gameRenderingLayer = new GameRenderingLayer(COURT_LENGTH, COURT_LENGTH / 2, COURT_LENGTH / 2);
        JJ.layers.addLayerTop(gameRenderingLayer);

        //Define your ThingSpecs
        JJ.specs
                .set("bot", bot())
                .set("player", player())
                .set("ball", ball())
                .set("wall", wall())
                .set("scoreWall", scoreWall())
        ;


        //Add instances of your ThingSpecs to a scene
        SceneSpec level = new SceneSpec()
                .add("player",
                        InstanceParams.New()
                                .setPosition(COURT_LENGTH - BAT_INSET, COURT_LENGTH / 2)
                                /**
                                 * Would not normally set keys as a property, but it shows how you can get
                                 * Instance customisation with properties
                                 */
                                .setProperty("upkey", Input.Keys.UP)
                                .setProperty("downkey", Input.Keys.DOWN)
                )
                .add("player",
                        InstanceParams.New()
                                .setPosition(BAT_INSET, COURT_LENGTH / 2)
                                .setProperty("upkey", Input.Keys.Q)
                                .setProperty("downkey", Input.Keys.A)
                )
                .add("ball",
                        InstanceParams.New().setUniqueName("ball").setPosition(COURT_LENGTH / 2, COURT_LENGTH / 2))
                .add("wall"
                        , InstanceParams.New().setPosition(COURT_LENGTH / 2, COURT_LENGTH - BAT_INSET)
                        , InstanceParams.New().setPosition(COURT_LENGTH / 2, BAT_INSET)
                )
                .add("scoreWall",
                        InstanceParams.New()
                                .setPosition(0, COURT_LENGTH / 2)
                                .setProperty("color", Color.BLUE),
                        InstanceParams.New()
                                .setPosition(COURT_LENGTH, COURT_LENGTH / 2)
                                .setProperty("color", Color.RED)
                );

        //Load the the scene
        JJ.things.loadNow(level, this::kickOff);
    }

    private void kickOff() {
        JJ.things.getThingByName("ball").physicsroot.getB2DBody().setLinearVelocity(-20, 0);
    }


    private ThingSpec bot() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D()
                        .setBodyType(BodyDef.BodyType.KinematicBody)
                )
                .addNode(
                        new NodeSpec()
                                .addRender(new ShapeRenderSpec.Rectangle().setDimension(BAT_WIDTH, BAT_HEIGHT).color.set(Color.WHITE))
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(BAT_WIDTH, BAT_HEIGHT))
                                        .addInitialBeginCollision(new BatCollision()))
                )
                .addBehaviour(
                        new RandomBotBehaviour()
                )
                ;
    }

    private ThingSpec player() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D()
                        .setBodyType(BodyDef.BodyType.KinematicBody)
                )
                .addNode(
                        new NodeSpec()
                                .addRender(new ShapeRenderSpec.Rectangle().setDimension(BAT_WIDTH, BAT_HEIGHT).color.set(Color.WHITE))
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(BAT_WIDTH, BAT_HEIGHT))
                                        .addInitialBeginCollision(new BatCollision())
                                )
                )
                .addBehaviour(new PlayerBehaviour())
                ;
    }

    private ThingSpec ball() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setLinearDamping(0).setFixedRotation(true))
                .addSound(
                        new SoundParams("pong").addPath("sounds/pong.mp3").setVolume(0.5f)
                )
                .addNode(
                        new NodeSpec()
                                .addRender(new ShapeRenderSpec.Rectangle().setDimension(5, 5).color.set(Color.BLUE))
                                .addPhysics(new FixtureNodeSpec()
                                        .setFriction(0)
                                        .setRestitution(1)
                                        .setShape(new B2DShapeSpec.Circle(2.5f))
                                        .addInitialBeginCollision(new SoundCollision("pong"))
                                )
                )
                ;
    }

    private ThingSpec wall() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.StaticBody))
                .addNode(
                        new NodeSpec()
                                .addRender(new ShapeRenderSpec.Rectangle().setDimension(COURT_LENGTH - 2 * (BAT_INSET + BAT_WIDTH * 0.501f), BAT_WIDTH).color.set(Color.WHITE))
                                .addPhysics(new FixtureNodeSpec().setShape(new B2DShapeSpec.PolygonRectangle(COURT_LENGTH - 2 * (BAT_INSET + BAT_WIDTH * 0.501f), BAT_WIDTH)))
                )
                ;
    }


    private ThingSpec scoreWall() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.StaticBody))
                .addNode(
                        new NodeSpec()
                                .addRender(new B2DRenderSpec().color.delegateToProperty("color"))
                                .addPhysics(new FixtureNodeSpec()
                                                .setShape(new B2DShapeSpec.PolygonRectangle(5, COURT_LENGTH * 1.1f))
                                        .addInitialBeginCollision(new EmitEventCollision())
                                )
                )
                ;
    }
}
