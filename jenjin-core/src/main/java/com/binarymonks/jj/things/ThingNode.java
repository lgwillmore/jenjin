package com.binarymonks.jj.things;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.physics.CollisionResolver;
import com.binarymonks.jj.render.RenderNode;

public class ThingNode {

    public Thing parent;
    public Fixture fixture;
    public RenderNode render;
    public ObjectMap<String, Object> properties = new ObjectMap<>();
    private CollisionResolver collisionResolver = new CollisionResolver();


    public boolean hasProperty(String propertyKey) {
        if (!properties.containsKey(propertyKey) && parent != null) {
            return parent.hasProperty(propertyKey);
        } else {
            return properties.containsKey(propertyKey);
        }

    }

    public CollisionResolver getCollisionResolver() {
        return collisionResolver;
    }
}

