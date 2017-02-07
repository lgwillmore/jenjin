package com.binarymonks.jj.render.specs;


import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.physics.specs.PhysicsNodeSpec;
import com.binarymonks.jj.render.RenderNode;
import com.binarymonks.jj.specs.PropField;
import com.binarymonks.jj.specs.SpecPropField;

public abstract class RenderSpec<CONCRETE extends RenderSpec> {
    public int id = Global.renderWorld.nextRenderID();
    public int layer;
    public int thingPriority;
    public SpecPropField<Color, CONCRETE> color = new SpecPropField<>((CONCRETE) this, new Color(0, 0, 0, 1));
    CONCRETE self;

    public RenderSpec() {
        self = (CONCRETE) this;
    }

    public CONCRETE setLayer(int layer) {
        this.layer = layer;
        return self;
    }

    public CONCRETE setPriority(int priority) {
        this.thingPriority = priority;
        return self;
    }

    public abstract RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec);

    public static class Null extends RenderSpec<Null> {


        @Override
        public RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec) {
            return RenderNode.NULL;
        }

    }

}
