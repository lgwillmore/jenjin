package com.binarymonks.jj.things.specs;

import com.badlogic.gdx.utils.Array;

/**
 * Created by lwillmore on 25/01/17.
 */
public class ThingSpec {

    Array<NodeSpec> nodeSpecs = new Array<>();

    public ThingSpec addNode(NodeSpec nodeSpec) {
        this.nodeSpecs.add(nodeSpec);
        return this;
    }
}
