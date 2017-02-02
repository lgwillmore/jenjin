package com.binarymonks.jj.things.specs;

import com.binarymonks.jj.physics.specs.PhysicsNodeSpec;
import com.binarymonks.jj.render.specs.RenderSpec;


public class NodeSpec {

    public RenderSpec renderSpec = new RenderSpec.Null();
    public PhysicsNodeSpec physicsNodeSpec = new PhysicsNodeSpec.Null();

    public NodeSpec addRender(RenderSpec renderSpec) {
        this.renderSpec = renderSpec;
        return this;
    }


    public NodeSpec addPhysics(PhysicsNodeSpec physicsNodeSpec) {
        this.physicsNodeSpec = physicsNodeSpec;
        return this;
    }
}
