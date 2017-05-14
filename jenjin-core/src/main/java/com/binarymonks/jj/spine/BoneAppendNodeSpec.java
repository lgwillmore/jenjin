package com.binarymonks.jj.spine;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.components.Component;
import com.binarymonks.jj.specs.render.RenderSpec;

public class BoneAppendNodeSpec {

    public RenderSpec renderSpec = new RenderSpec.Null();
    public BonePhysicsNodeSpec physicsNodeSpec;
    public ObjectMap<String, Object> properties = new ObjectMap<>();
    public Array<Component> components = new Array<>();
    public String name;

    public BoneAppendNodeSpec setName(String name) {
        this.name = name;
        return this;
    }

    public BoneAppendNodeSpec setRender(RenderSpec renderSpec) {
        this.renderSpec = renderSpec;
        return this;
    }

    public BoneAppendNodeSpec setPhysics(BonePhysicsNodeSpec physicsNodeSpec) {
        this.physicsNodeSpec = physicsNodeSpec;
        return this;
    }

    public BoneAppendNodeSpec setProperty(String key, Object value){
        properties.put(key,value);
        return this;
    }

    public BoneAppendNodeSpec setProperty(String key){
        properties.put(key, null);
        return this;
    }

    public BoneAppendNodeSpec addComponent(Component component) {
        components.add(component);
        return this;
    }

}
