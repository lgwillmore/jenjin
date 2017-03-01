package com.binarymonks.jj.spine;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.specs.spine.SpineSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.ThingFactory;
import com.binarymonks.jj.things.ThingNode;

public class SpineFactory extends ThingFactory<SpineSpec> {


    @Override
    protected Array<ThingNode> buildNodes(Thing thing, SpineSpec thingSpec, InstanceParams instanceParams, Body body) {
        Array<ThingNode> nodes =  super.buildNodes(thing, thingSpec, instanceParams, body);

        return nodes;
    }
}
