package com.binarymonks.jj.things;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.audio.SoundEffects;
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
    public SoundEffects sounds;
    BehaviourRoot behaviour = new BehaviourRoot();
    boolean markedForDestruction = false;
    ObjectMap<String, Object> properties = new ObjectMap<>();
    ObjectMap<String, ThingNode> nodes = new ObjectMap<>();


    public Thing(String path, int id, String uniqueName) {
        this.path = path;
        this.id = id;
        this.uniqueName = uniqueName;
    }

    public boolean hasProperty(String propertyKey) {
        return properties.containsKey(propertyKey);
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public void markForDestruction() {
        markedForDestruction = true;
        Global.thingWorld.remove(this);
    }

    public boolean isMarkedForDestruction() {
        return markedForDestruction;
    }


}
