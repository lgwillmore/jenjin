package com.binarymonks.jj.things;

import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.physics.PhysicsRoot;
import com.binarymonks.jj.render.RenderRoot;

public class Thing {

    public int id;
    RenderRoot renderRoot;
    PhysicsRoot physicsroot;

    boolean markedForDestruction=false;

    public Thing(int id) {
        this.id = id;
    }

    public void markForDestruction(){
        markedForDestruction=true;
        Global.thingGraphs.remove(this);
    }

    public boolean isMarkedForDestruction(){
        return markedForDestruction;
    }
}
