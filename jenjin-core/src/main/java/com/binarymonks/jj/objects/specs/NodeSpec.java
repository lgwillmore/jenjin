package com.binarymonks.jj.objects.specs;

import com.binarymonks.jj.render.specs.RenderSpec;

/**
 * Created by lwillmore on 25/01/17.
 */
public class NodeSpec {

    RenderSpec renderSpec = new RenderSpec.Null();

    public NodeSpec addRender(RenderSpec renderSpec) {
        this.renderSpec=renderSpec;
        return this;
    }


}
