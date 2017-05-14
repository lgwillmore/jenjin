package com.binarymonks.jj.spine;

import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.components.Component;
import com.binarymonks.jj.specs.lights.LightSpec;

public class BoneAppendSpec {

    public Array<BoneAppendNodeSpec> nodes = new Array<>();

    public Array<Component> components = new Array<>();

    public Array<LightSpec> lights = new Array<>();

    public BoneAppendSpec addLight(LightSpec light){
        lights.add(light);
        return this;
    }

    public BoneAppendSpec addNode(BoneAppendNodeSpec thingNodeSpec) {
        nodes.add(thingNodeSpec);
        return this;
    }

    public BoneAppendNodeSpec newNode(){
        BoneAppendNodeSpec thingNodeSpec = new BoneAppendNodeSpec();
        nodes.add(thingNodeSpec);
        return thingNodeSpec;
    }

    public BoneAppendSpec addComponent(Component component) {
        components.add(component);
        return this;
    }

}
