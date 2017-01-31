package com.binarymonks.jj.things;

import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.physics.PhysicsRoot;
import com.binarymonks.jj.render.RenderRoot;

public class Thing {

    public String path;
    public int id;
    public RenderRoot renderRoot=new RenderRoot();

    public PhysicsRoot physicsroot;
    boolean markedForDestruction = false;

    public Thing(String path, int id) {
        this.path = path;
        this.id = id;
    }

    public void markForDestruction() {
        markedForDestruction = true;
        Global.thingWorld.remove(this);
    }

    public boolean isMarkedForDestruction() {
        return markedForDestruction;
    }
}
