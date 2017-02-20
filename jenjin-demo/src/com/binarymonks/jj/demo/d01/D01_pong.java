package com.binarymonks.jj.demo.d01;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.audio.SoundParams;
import com.binarymonks.jj.input.Actions;
import com.binarymonks.jj.layers.GameRenderingLayer;
import com.binarymonks.jj.physics.collisions.DestroyCollision;
import com.binarymonks.jj.physics.collisions.EmitEventCollision;
import com.binarymonks.jj.physics.collisions.SoundCollision;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;
import com.binarymonks.jj.specs.B2DCompositeSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.FixtureNodeSpec;
import com.binarymonks.jj.specs.render.RenderBuilder;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.specs.NodeSpec;
import com.binarymonks.jj.specs.ThingSpec;

public class D01_pong extends Game {

    public static float COURT_LENGTH = 100;
    public static float BAT_HEIGHT = 20;
    public static float BAT_WIDTH = 5;
    public static float BAT_INSET = 5;

    public static String MSG_PLAYER1_SCORE = "player1_scored";
    public static String MSG_PLAYER2_SCORE = "player2_scored";

    public D01_pong(com.binarymonks.jj.JJConfig JJConfig) {
        super(JJConfig);
    }

    @Override
    protected void gameOn() {
        GameRenderingLayer gameRenderingLayer = new GameRenderingLayer(COURT_LENGTH, COURT_LENGTH / 2, COURT_LENGTH / 2);
        JJ.layers.addLayerTop(gameRenderingLayer);

        //Define your ThingSpecs
        JJ.specs
                .set("player", player())
                .set("ball", ball())
                .set("wall", wall())
                .set("scoreWall", scoreWall())
        ;

        //Add instances of your ThingSpecs to a scene
        B2DCompositeSpec level = new B2DCompositeSpec();
        level.addThingSpec("player",
                InstanceParams.New()
                        .setUniqueName("player2")
                        .setPosition(COURT_LENGTH - BAT_INSET, COURT_LENGTH / 2)
                        .setProperty("color", Color.BLUE)
        );
        level.addThingSpec("player",
                InstanceParams.New()
                        .setUniqueName("player1")
                        .setPosition(BAT_INSET, COURT_LENGTH / 2)
                        .setProperty("color", Color.RED)
        );
        level.addThingSpec("ball",
                InstanceParams.New()
                        .setUniqueName("ball")
                        .setPosition(COURT_LENGTH / 2, COURT_LENGTH / 2));
        level.addThingSpec("wall", InstanceParams.New().setPosition(COURT_LENGTH / 2, COURT_LENGTH - BAT_HEIGHT / 2));
        level.addThingSpec("wall", InstanceParams.New().setPosition(COURT_LENGTH / 2, BAT_HEIGHT / 2));
        level.addThingSpec("scoreWall",
                InstanceParams.New()
                        .setPosition(COURT_LENGTH, COURT_LENGTH / 2)
                        .setProperty("color", Color.RED)
                        .setProperty("score_message", MSG_PLAYER2_SCORE)
        );
        level.addThingSpec("scoreWall",
                InstanceParams.New()
                        .setPosition(0, COURT_LENGTH / 2)
                        .setProperty("color", Color.BLUE)
                        .setProperty("score_message", MSG_PLAYER1_SCORE));

        //Register some event handlers for when the ball gets passed a player
        JJ.events.register(MSG_PLAYER1_SCORE, this::player1Scored);
        JJ.events.register(MSG_PLAYER2_SCORE, this::player2Scored);

        //Load the the scene
        JJ.things.load(level, this::gameLoaded);
    }

    private void gameLoaded() {
        //Wire some controls into each player instance
        PlayerBehaviour player1 = JJ.things.getThingByName("player1").getBehaviour(PlayerBehaviour.class);
        mapPlayerKeys(player1, Input.Keys.W, Input.Keys.S);
        PlayerBehaviour player2 = JJ.things.getThingByName("player2").getBehaviour(PlayerBehaviour.class);
        mapPlayerKeys(player2, Input.Keys.UP, Input.Keys.DOWN);

        kickOff(JJ.things.getThingByName("ball"));
    }

    private void mapPlayerKeys(PlayerBehaviour player, int up, int down) {
        JJ.input.map(up, Actions.Key.PRESSED, player::goUp);
        JJ.input.map(up, Actions.Key.RELEASED, player::stopUp);
        JJ.input.map(down, Actions.Key.PRESSED, player::goDown);
        JJ.input.map(down, Actions.Key.RELEASED, player::stopDown);
    }

    private void kickOff(Thing ball) {
        ball.physicsroot.getB2DBody().setLinearVelocity(-20, 0);
    }

    private void player1Scored() {
        newBall();
    }

    private void player2Scored() {
        newBall();
    }

    private void newBall() {
        JJ.things.create(
                "ball",
                InstanceParams.New()
                        .setUniqueName("ball")
                        .setPosition(COURT_LENGTH / 2, COURT_LENGTH / 2),
                this::kickOff
        );
    }

    private ThingSpec player() {
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D()
                        .setBodyType(BodyDef.BodyType.KinematicBody)
                )
                .addNode(
                        new NodeSpec()
                                .addRender(RenderBuilder.shapeRectangle(BAT_WIDTH, BAT_HEIGHT)
                                        .delegateColorTo("color")
                                        .build()
                                )
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
                                .addRender(RenderBuilder.shapeRectangle(5, 5).setColor(Color.GREEN).build())
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
                                .addRender(RenderBuilder.shapeRectangle(COURT_LENGTH - 2 * (BAT_INSET + BAT_WIDTH * 0.501f), BAT_WIDTH)
                                        .setColor(Color.WHITE)
                                        .build()
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(COURT_LENGTH - 2 * (BAT_INSET + BAT_WIDTH * 0.501f), BAT_WIDTH)
                                        ))
                )
                ;
    }

    private ThingSpec scoreWall() {
        EmitEventCollision emitEventCollision = new EmitEventCollision();
        emitEventCollision.message.delegateToProperty("score_message");
        return new ThingSpec()
                .setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.StaticBody))
                .addNode(
                        new NodeSpec()
                                .addRender(RenderBuilder.b2d().delegateColorTo("color").build())
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(5, COURT_LENGTH * 1.1f))
                                        .addInitialBeginCollision(emitEventCollision)
                                        .addFinalBeginCollision(new DestroyCollision(false, true))
                                )
                )
                ;
    }
}
