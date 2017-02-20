package com.binarymonks.jj.render;

import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.specs.render.AnimatedRenderSpec;
import com.binarymonks.jj.specs.render.RenderSpec;
import com.binarymonks.jj.things.InstanceParams;

/**
 * Created by lwillmore on 18/02/17.
 */
public class RenderFactory {


    public RenderNode build(RenderSpec renderSpec, InstanceParams instance, PhysicsNodeSpec physicsNodeSpec) {
        if (renderSpec instanceof AnimatedRenderSpec) {
            return buildAnimated((AnimatedRenderSpec) renderSpec, instance, physicsNodeSpec);
        }
        return null;
    }

    private RenderNode buildAnimated(AnimatedRenderSpec renderSpec, InstanceParams instance, PhysicsNodeSpec physicsNodeSpec) {
        return null;
    }

}
