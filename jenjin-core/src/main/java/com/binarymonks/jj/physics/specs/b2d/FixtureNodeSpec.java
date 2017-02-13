package com.binarymonks.jj.physics.specs.b2d;

import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.physics.CollisionDataSpec;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.physics.specs.PhysicsNodeSpec;

public class FixtureNodeSpec implements PhysicsNodeSpec {

    public CollisionDataSpec<FixtureNodeSpec> collisionData = new CollisionDataSpec<>(this);
    public float density = 0.5f;
    public float friction = 0.1f;
    public float restitution = 0;
    public float rotationD = 0;
    public float offsetX;
    public float offsetY;
    public boolean isSensor = false;
    public B2DShapeSpec shape;
    public Array<CollisionFunction> initialBeginCollisions = new Array<>();
    public Array<CollisionFunction> finalBeginCollisions = new Array<>();
    public Array<CollisionFunction> endCollisions = new Array<>();


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

    public FixtureNodeSpec setOffset(float x, float y) {
        this.offsetX = x;
        this.offsetY = y;
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

    public FixtureNodeSpec addInitialBeginCollision(CollisionFunction function) {
        initialBeginCollisions.add(function);
        return this;
    }

    public FixtureNodeSpec addFinalBeginCollision(CollisionFunction function) {
        finalBeginCollisions.add(function);
        return this;
    }

    public FixtureNodeSpec addEndCollision(CollisionFunction function) {
        endCollisions.add(function);
        return this;
    }

    @Override
    public float getOffsetX() {
        return offsetX;
    }

    @Override
    public float getOffsetY() {
        return offsetY;
    }

    @Override
    public float getRotationD() {
        return rotationD;
    }
}
