package com.binarymonks.jj.demo.d07;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.binarymonks.jj.Game;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.components.ForceMovement;
import com.binarymonks.jj.specs.SceneSpec;
import com.binarymonks.jj.things.InstanceParams;

public class Swing extends Game {

    float WORLD_WIDTH = 100;
    float WORLD_HEIGHT = 100;

    public Swing(JJConfig jjconfig) {
        super(jjconfig);
    }

    @Override
    protected void gameOn() {

        JJ.specs.set("player", GameSpecs.player());
        JJ.specs.set("link", GameSpecs.link());
        JJ.specs.set("ball", GameSpecs.ball());

        float linkOffset=0.45f;
        SceneSpec scene = new SceneSpec();
        int player = scene.addThingSpec("player", InstanceParams.New().setPosition(WORLD_WIDTH / 2, WORLD_HEIGHT / 2).setUniqueName("player"));
        float lastLinkY = WORLD_HEIGHT / 2+linkOffset*2;
        int lastLink = scene.addThingSpec("link", InstanceParams.New().setPosition(WORLD_WIDTH / 2, lastLinkY));
        RevoluteJointDef revJoint = new RevoluteJointDef();
        revJoint.localAnchorA.set(0, 0);
        revJoint.localAnchorB.set(0, -linkOffset);
        revJoint.enableLimit = false;
        revJoint.collideConnected=false;
        scene.addJoint(player, lastLink, revJoint);
        for (int i = 0; i < 20; i++) {
            lastLinkY=lastLinkY+linkOffset*2;
            int newLink = scene.addThingSpec("link", InstanceParams.New().setPosition(WORLD_WIDTH / 2, lastLinkY));
            revJoint = new RevoluteJointDef();
            revJoint.localAnchorA.set(0, linkOffset);
            revJoint.localAnchorB.set(0, -linkOffset);
            revJoint.enableLimit = false;
            revJoint.collideConnected=false;
            scene.addJoint(lastLink, newLink, revJoint);
            lastLink = newLink;
        }
        lastLinkY=lastLinkY+linkOffset*2;
        int ball=scene.addThingSpec("ball", InstanceParams.New().setPosition(WORLD_WIDTH / 2, lastLinkY));
        revJoint = new RevoluteJointDef();
        revJoint.localAnchorA.set(0, 0);
        revJoint.localAnchorB.set(0, linkOffset);
        revJoint.enableLimit = false;
        revJoint.collideConnected=false;
        scene.addJoint(ball, lastLink, revJoint);

        scene.addThingSpec("ball", InstanceParams.New().setPosition(30, 30));
        scene.addThingSpec("ball", InstanceParams.New().setPosition(80, 80));
        scene.addThingSpec("ball", InstanceParams.New().setPosition(30, 80));
        scene.addThingSpec("ball", InstanceParams.New().setPosition(80, 30));

        JJ.things.load(scene, this::onLoad);

    }

    private void onLoad() {
        ForceMovement playerEngine = JJ.things.getThingByName("player").getComponent(ForceMovement.class);
        KeyboardControls keyboardControls = new KeyboardControls(playerEngine);
        JJ.input.map(Input.Keys.UP,keyboardControls::up);
        JJ.input.map(Input.Keys.DOWN,keyboardControls::down);
        JJ.input.map(Input.Keys.LEFT,keyboardControls::left);
        JJ.input.map(Input.Keys.RIGHT,keyboardControls::right);
    }
}
