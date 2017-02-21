package com.binarymonks.jj.specs.physics;

import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.b2d.FixtureNodeSpec;


public class FixtureBuilder {

    FixtureNodeSpec spec = new FixtureNodeSpec();

    public FixtureBuilder setDensity(float density) {
        spec.density = density;
        return this;
    }

    public FixtureBuilder setFriction(float friction) {
        spec.friction = friction;
        return this;
    }

    public FixtureBuilder setRestitution(float restitution) {
        spec.restitution = restitution;
        return this;
    }

    public FixtureBuilder setRotationD(float rotationD) {
        spec.rotationD = rotationD;
        return this;
    }

    public FixtureBuilder setOffset(float x, float y) {
        spec.offsetX = x;
        spec.offsetY = y;
        return this;
    }

    public FixtureBuilder setSensor(boolean sensor) {
        spec.isSensor = sensor;
        return this;
    }

    public FixtureBuilder setShape(B2DShapeSpec shape) {
        spec.shape = shape;
        return this;
    }

    public FixtureBuilder addInitialBeginCollision(CollisionFunction function) {
        spec.initialBeginCollisions.add(function);
        return this;
    }

    public FixtureBuilder addFinalBeginCollision(CollisionFunction function) {
        spec.finalBeginCollisions.add(function);
        return this;
    }

    public FixtureBuilder addEndCollision(CollisionFunction function) {
        spec.endCollisions.add(function);
        return this;
    }

    public FixtureBuilder setCollisionsToGroup(String name) {
        spec.collisionData.setToGroup(name);
        return this;
    }

    public FixtureBuilder setCollisionsToExplicit(short category, short mask) {
        spec.collisionData.setToExplicit(category, mask);
        return this;
    }

    public PhysicsNodeSpec build() {
        return spec;
    }
}
