package com.binarymonks.jj.specs;

import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.specs.render.RenderSpec;


public class ThingNodeSpec {

    public RenderSpec renderSpec = new RenderSpec.Null();
    public PhysicsNodeSpec physicsNodeSpec = new PhysicsNodeSpec.Null();
    public String name;

    public ThingNodeSpec setName(String name) {
        this.name = name;
        return this;
    }

    public ThingNodeSpec setRender(RenderSpec renderSpec) {
        this.renderSpec = renderSpec;
        return this;
    }


    public ThingNodeSpec setPhysics(PhysicsNodeSpec physicsNodeSpec) {
        this.physicsNodeSpec = physicsNodeSpec;
        return this;
    }
}
