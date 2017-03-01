package com.binarymonks.jj.spine;

import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.specs.render.RenderSpec;
import com.binarymonks.jj.specs.spine.SpineSpec;
import com.binarymonks.jj.things.InstanceParams;

public class SpineRenderSpec extends RenderSpec {

    SpineSpec spineSpec;

    public SpineRenderSpec(SpineSpec spineSpec) {
        this.spineSpec = spineSpec;
    }

    @Override
    public RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec, InstanceParams instanceParams) {
        return new SpineRenderNode(this);
    }
}
