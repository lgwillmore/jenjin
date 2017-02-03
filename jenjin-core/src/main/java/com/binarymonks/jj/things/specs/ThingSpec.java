package com.binarymonks.jj.things.specs;

import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.behaviour.Behaviour;
import com.binarymonks.jj.physics.PhysicsRoot;
import com.binarymonks.jj.physics.specs.PhysicsRootSpec;

/**
 * Created by lwillmore on 25/01/17.
 */
public class ThingSpec {

    public Array<NodeSpec> nodeSpecs = new Array<>();

    public Array<Behaviour> behaviours = new Array<>();

    public PhysicsRootSpec physicsRootSpec = new PhysicsRootSpec.B2D();


    public ThingSpec addNode(NodeSpec nodeSpec) {
        this.nodeSpecs.add(nodeSpec);
        return this;
    }

    public ThingSpec addBehaviour(Behaviour behaviour) {
        behaviours.add(behaviour);
        return this;
    }

}
