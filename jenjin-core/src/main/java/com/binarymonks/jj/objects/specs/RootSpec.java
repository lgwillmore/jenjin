package com.binarymonks.jj.objects.specs;

import com.badlogic.gdx.utils.Array;

/**
 * Created by lwillmore on 25/01/17.
 */
public class RootSpec {

    Array<NodeSpec> nodeSpecs = new Array<>();

    public RootSpec addNode(NodeSpec nodeSpec) {
        this.nodeSpecs.add(nodeSpec);
        return this;
    }
}
