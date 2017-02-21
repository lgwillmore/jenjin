package com.binarymonks.jj.specs;

import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.specs.render.RenderSpec;


public class NodeSpec {

    public RenderSpec renderSpec = new RenderSpec.Null();
    public PhysicsNodeSpec physicsNodeSpec = new PhysicsNodeSpec.Null();
    public String name;

    public NodeSpec setName(String name) {
        this.name = name;
        return this;
    }

    public NodeSpec setRender(RenderSpec renderSpec) {
        this.renderSpec = renderSpec;
        return this;
    }


    public NodeSpec setPhysics(PhysicsNodeSpec physicsNodeSpec) {
        this.physicsNodeSpec = physicsNodeSpec;
        return this;
    }
}
