package com.binarymonks.jj.things;

import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.behaviour.BehaviourRoot;
import com.binarymonks.jj.physics.PhysicsRoot;
import com.binarymonks.jj.render.RenderRoot;

public class Thing {

    public String path;
    public int id;
    public String uniqueName;
    public RenderRoot renderRoot = new RenderRoot();
    public PhysicsRoot physicsroot;
    public BehaviourRoot behaviourRoot = new BehaviourRoot();
    boolean markedForDestruction = false;

    public Thing(String path, int id, String uniqueName) {
        this.path = path;
        this.id = id;
        this.uniqueName = uniqueName;
    }

    public void markForDestruction() {
        markedForDestruction = true;
        Global.thingWorld.remove(this);
    }

    public boolean isMarkedForDestruction() {
        return markedForDestruction;
    }
}
