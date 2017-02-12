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
import com.binarymonks.jj.physics.specs.PhysicsRootSpec;
import com.binarymonks.jj.physics.specs.b2d.B2DShapeSpec;
import com.binarymonks.jj.physics.specs.b2d.FixtureNodeSpec;
import com.binarymonks.jj.render.specs.B2DRenderSpec;
import com.binarymonks.jj.render.specs.ShapeRenderSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.specs.NodeSpec;
import com.binarymonks.jj.things.specs.SceneSpec;
import com.binarymonks.jj.things.specs.ThingSpec;

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
        SceneSpec level = new SceneSpec()
                .add("player",
                        InstanceParams.New()
                                .setUniqueName("player2")
                                .setPosition(COURT_LENGTH - BAT_INSET, COURT_LENGTH / 2)
                                .setProperty("color", Color.BLUE)
                )
                .add("player",
                        InstanceParams.New()
                                .setUniqueName("player1")
                                .setPosition(BAT_INSET, COURT_LENGTH / 2)
                                .setProperty("color", Color.RED)
                )
                .add("ball",
                        InstanceParams.New()
                                .setUniqueName("ball")
                                .setPosition(COURT_LENGTH / 2, COURT_LENGTH / 2))
                .add("wall"
                        , InstanceParams.New().setPosition(COURT_LENGTH / 2, COURT_LENGTH - BAT_HEIGHT / 2)
                        , InstanceParams.New().setPosition(COURT_LENGTH / 2, BAT_HEIGHT / 2)
                )
                .add("scoreWall",
                        InstanceParams.New()
                                .setPosition(0, COURT_LENGTH / 2)
                                .setProperty("color", Color.BLUE)
                                .setProperty("score_message", MSG_PLAYER1_SCORE),
                        InstanceParams.New()
                                .setPosition(COURT_LENGTH, COURT_LENGTH / 2)
                                .setProperty("color", Color.RED)
                                .setProperty("score_message", MSG_PLAYER2_SCORE)
                );

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
                                .addRender(new ShapeRenderSpec.Rectangle()
                                        .setDimension(BAT_WIDTH, BAT_HEIGHT)
                                        .color.delegateToProperty("color")
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
                                .addRender(new ShapeRenderSpec.Rectangle().setDimension(5, 5).color.set(Color.GREEN))
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
                                .addRender(new ShapeRenderSpec.Rectangle()
                                        .setDimension(COURT_LENGTH - 2 * (BAT_INSET + BAT_WIDTH * 0.501f), BAT_WIDTH)
                                        .color.set(Color.WHITE)
                                )
                                .addPhysics(new FixtureNodeSpec()
                                        .setShape(new B2DShapeSpec.PolygonRectangle(COURT_LENGTH - 2 * (BAT_INSET + BAT_WIDTH * 0.501f), BAT_WIDTH)
                                        ))
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
                                        .addInitialBeginCollision(new EmitEventCollision().message.delegateToProperty("score_message"))
                                        .addFinalBeginCollision(new DestroyCollision(false, true))
                                )
                )
                ;
    }
}
