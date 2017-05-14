package com.binarymonks.jj.things;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.physics.CollisionResolver;
import com.binarymonks.jj.render.nodes.RenderNode;

public class ThingNode {

    public Thing parent;
    public Fixture fixture;
    public RenderNode render;
    public ObjectMap<String, Object> properties = new ObjectMap<>();
    public CollisionResolver collisionResolver = new CollisionResolver();
    public String name;

    public ThingNode(String name) {
        this.name = name;
    }

    public boolean hasProperty(String propertyKey) {
        if (!properties.containsKey(propertyKey) && parent != null) {
            return parent.hasProperty(propertyKey);
        } else {
            return properties.containsKey(propertyKey);
        }
    }

    public <T> T getProperty(String propertyKey){
        if (!properties.containsKey(propertyKey) && parent != null) {
            return parent.getProperty(propertyKey);
        } else {
            return (T) properties.get(propertyKey);
        }
    }

    public CollisionResolver getCollisionResolver() {
        return collisionResolver;
    }
}

