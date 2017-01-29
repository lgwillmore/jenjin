package com.binarymonks.jj.physics.specs;

import com.badlogic.gdx.physics.box2d.BodyDef;

public interface PhysicsRootSpec {

    public static class Basic implements PhysicsRootSpec {
    }

    public static class B2D implements PhysicsRootSpec {
        public BodyDef.BodyType bodyType = BodyDef.BodyType.DynamicBody;
        public boolean fixedRotation;
        public float linearDamping;
        public float angularDamping;
        public boolean bullet = false;
        public boolean allowSleep = true;
    }
}
