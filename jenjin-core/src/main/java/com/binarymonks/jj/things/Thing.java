package com.binarymonks.jj.things;

import box2dLight.Light;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.audio.SoundEffects;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.components.Component;
import com.binarymonks.jj.components.ComponentMaster;
import com.binarymonks.jj.physics.PhysicsRoot;
import com.binarymonks.jj.render.RenderRoot;
import com.binarymonks.jj.specs.SceneNodeSpec;
import com.binarymonks.jj.specs.ThingSpec;

public class Thing {

    public String path;
    public int id;
    public String uniqueName;
    public SceneNodeSpec spec;
    public RenderRoot renderRoot = new RenderRoot();
    public PhysicsRoot physicsroot;
    public SoundEffects sounds;
    boolean markedForDestruction = false;
    public ComponentMaster componentMaster = new ComponentMaster();
    ObjectMap<String, Light> lights = new ObjectMap<>();
    ObjectMap<String, Object> properties = new ObjectMap<>();
    ObjectMap<String, ThingNode> nodes = new ObjectMap<>();
    public boolean pool=true;


    public Thing(String path, int id, String uniqueName, SceneNodeSpec spec) {
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

    public void addComponent(Component component) {
        component.setParent(this);
        componentMaster.addComponent(component);
    }

    public boolean isMarkedForDestruction() {
        return markedForDestruction;
    }


    public void update() {
        componentMaster.update();
    }

    public <T extends Component> T getComponent(Class<T> componentType) {
        return componentMaster.getTrackedComponent((Class<Component>) componentType);
    }

    public void setPool(boolean pool) {
        this.pool = pool;
    }
}
