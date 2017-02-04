package com.binarymonks.jj.things.specs;

import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.audio.SoundParams;
import com.binarymonks.jj.behaviour.Behaviour;
import com.binarymonks.jj.physics.specs.PhysicsRootSpec;


public class ThingSpec {

    public Array<NodeSpec> nodes = new Array<>();

    public Array<Behaviour> behaviour = new Array<>();

    public PhysicsRootSpec physics = new PhysicsRootSpec.B2D();

    public Array<SoundParams> sounds = new Array<>();

    public ThingSpec setPhysics(PhysicsRootSpec physicsSpec) {
        this.physics = physicsSpec;
        return this;
    }

    public ThingSpec addNode(NodeSpec nodeSpec) {
        this.nodes.add(nodeSpec);
        return this;
    }

    public ThingSpec addBehaviour(Behaviour behaviour) {
        this.behaviour.add(behaviour);
        return this;
    }

    public ThingSpec addSound(SoundParams soundParams) {
        sounds.add(soundParams);
        return this;
    }
}
