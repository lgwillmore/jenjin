package com.binarymonks.jj.specs;

import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.audio.SoundParams;
import com.binarymonks.jj.components.Component;
import com.binarymonks.jj.specs.lights.LightSpec;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;


public class ThingSpec implements SceneNodeSpec{

    public Array<ThingNodeSpec> nodes = new Array<>();

    public Array<Component> components = new Array<>();

    public PhysicsRootSpec physics = new PhysicsRootSpec.B2D();

    public Array<SoundParams> sounds = new Array<>();

    public Array<LightSpec> lights = new Array<>();

    public boolean pool = true;

    public ThingSpec setPhysics(PhysicsRootSpec physicsSpec) {
        this.physics = physicsSpec;
        return this;
    }

    public ThingSpec addNode(ThingNodeSpec thingNodeSpec) {
        nodes.add(thingNodeSpec);
        return this;
    }

    public ThingNodeSpec newNode(){
        ThingNodeSpec thingNodeSpec = new ThingNodeSpec();
        nodes.add(thingNodeSpec);
        return thingNodeSpec;
    }

    public ThingSpec addComponent(Component component) {
        components.add(component);
        return this;
    }

    public ThingSpec addSound(SoundParams soundParams) {
        sounds.add(soundParams);
        return this;
    }

    public ThingSpec addLight(LightSpec light){
        lights.add(light);
        return this;
    }

    public ThingSpec setPool(boolean pool) {
        this.pool = pool;
        return this;
    }
}
