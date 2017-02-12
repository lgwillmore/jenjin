package com.binarymonks.jj.things;

import box2dLight.Light;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.async.Task;
import com.binarymonks.jj.async.TaskMaster;
import com.binarymonks.jj.audio.SoundEffects;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.behaviour.Behaviour;
import com.binarymonks.jj.behaviour.BehaviourMaster;
import com.binarymonks.jj.physics.PhysicsRoot;
import com.binarymonks.jj.render.RenderRoot;
import com.binarymonks.jj.things.specs.ThingSpec;

public class Thing {

    public String path;
    public int id;
    public String uniqueName;
    public ThingSpec spec;
    public RenderRoot renderRoot = new RenderRoot();
    public PhysicsRoot physicsroot;
    public SoundEffects sounds;
    boolean markedForDestruction = false;
    public BehaviourMaster behaviourMaster = new BehaviourMaster();
    ObjectMap<String, Light> lights = new ObjectMap<>();
    ObjectMap<String, Object> properties = new ObjectMap<>();
    ObjectMap<String, ThingNode> nodes = new ObjectMap<>();


    public Thing(String path, int id, String uniqueName, ThingSpec spec) {
        this.path = path;
        this.id = id;
        this.uniqueName = uniqueName;
        this.spec = spec;
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

    public void addBehaviour(Behaviour behaviour) {
        behaviour.setParent(this);
        behaviourMaster.addBehavior(behaviour);
    }

    public boolean isMarkedForDestruction() {
        return markedForDestruction;
    }


    public void update() {
        behaviourMaster.update();
    }

    public <T extends Behaviour> T getBehaviour(Class<T> behaviourType) {
        return behaviourMaster.getTrackedBehaviour((Class<Behaviour>) behaviourType);
    }
}
