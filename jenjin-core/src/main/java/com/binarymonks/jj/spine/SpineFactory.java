package com.binarymonks.jj.spine;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.specs.spine.SpineSpec;
import com.binarymonks.jj.things.*;

public class SpineFactory extends ThingFactory<SpineSpec> {


    @Override
    protected Array<ThingNode> buildNodes(Thing thing, SpineSpec thingSpec, InstanceParams instanceParams, Body body) {
        Array<ThingNode> nodes =  super.buildNodes(thing, thingSpec, instanceParams, body);
        SpineRenderSpec renderSpec = new SpineRenderSpec(thingSpec);

        ThingNode node = new ThingNode(null);

        RenderNode render = renderSpec.makeNode(null, instanceParams);
        node.render = render;
        nodes.add(node);

        ThingTools.addNodeToThing(thing,node);

        return nodes;
    }
}
