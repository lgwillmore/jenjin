package com.binarymonks.jj.things.specs;

import com.binarymonks.jj.physics.specs.PhysicsSpec;
import com.binarymonks.jj.render.specs.RenderSpec;


public class NodeSpec {

    RenderSpec renderSpec = new RenderSpec.Null();
    PhysicsSpec physicsSpec = new PhysicsSpec.Null();

    public NodeSpec addRender(RenderSpec renderSpec) {
        this.renderSpec = renderSpec;
        return this;
    }


    public NodeSpec addPhysics(PhysicsSpec physicsSpec) {
        this.physicsSpec = physicsSpec;
        return this;
    }
}
