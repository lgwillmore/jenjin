package com.binarymonks.jj.physics.specs.b2d;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.physics.specs.PhysicsNodeSpec;
import com.binarymonks.jj.pools.N;

public class FixtureNodeSpec implements PhysicsNodeSpec {

    public String collisionGroup;
    public float density = 0.5f;
    public float friction = 0f;
    public float restitution = 0;
    public float rotationD = 0;
    public Vector2 offset = N.ew(Vector2.class);
    public boolean isSensor = false;
    public B2DShapeSpec shape = new B2DShapeSpec.PolygonSquare(5, 5);

    public FixtureNodeSpec setCollisionGroup(String collisionGroup) {
        this.collisionGroup = collisionGroup;
        return this;
    }

    public FixtureNodeSpec setDensity(float density) {
        this.density = density;
        return this;
    }

    public FixtureNodeSpec setFriction(float friction) {
        this.friction = friction;
        return this;
    }

    public FixtureNodeSpec setRestitution(float restitution) {
        this.restitution = restitution;
        return this;
    }

    public FixtureNodeSpec setRotationD(float rotationD) {
        this.rotationD = rotationD;
        return this;
    }

    public FixtureNodeSpec setOffset(Vector2 offset) {
        this.offset = offset;
        return this;
    }

    public FixtureNodeSpec setSensor(boolean sensor) {
        this.isSensor = sensor;
        return this;
    }

    public FixtureNodeSpec setShape(B2DShapeSpec shape) {
        this.shape = shape;
        return this;
    }
}
