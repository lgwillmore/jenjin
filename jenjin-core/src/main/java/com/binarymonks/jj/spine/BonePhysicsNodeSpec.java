package com.binarymonks.jj.spine;

import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.physics.CollisionDataSpec;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;

public interface BonePhysicsNodeSpec {

    public static class B2DBoneFixture implements BonePhysicsNodeSpec {

        public CollisionDataSpec collisionData = new CollisionDataSpec();
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


        public B2DBoneFixture setDensity(float density) {
            this.density = density;
            return this;
        }

        public B2DBoneFixture setFriction(float friction) {
            this.friction = friction;
            return this;
        }

        public B2DBoneFixture setRestitution(float restitution) {
            this.restitution = restitution;
            return this;
        }

        public B2DBoneFixture setRotationD(float rotationD) {
            this.rotationD = rotationD;
            return this;
        }

        public B2DBoneFixture setOffset(float x, float y) {
            this.offsetX = x;
            this.offsetY = y;
            return this;
        }

        public B2DBoneFixture setSensor(boolean sensor) {
            this.isSensor = sensor;
            return this;
        }

        public B2DBoneFixture setShape(B2DShapeSpec shape) {
            this.shape = shape;
            return this;
        }

        public B2DBoneFixture addInitialBeginCollision(CollisionFunction function) {
            initialBeginCollisions.add(function);
            return this;
        }

        public B2DBoneFixture addFinalBeginCollision(CollisionFunction function) {
            finalBeginCollisions.add(function);
            return this;
        }

        public B2DBoneFixture addEndCollision(CollisionFunction function) {
            endCollisions.add(function);
            return this;
        }
    }

    public static class BoneShadowNode implements BonePhysicsNodeSpec {

        public CollisionDataSpec collisionData = new CollisionDataSpec();
        public float density = 0.5f;
        public float friction = 0.1f;
        public float restitution = 0;
        public float rotationD = 0;
        public boolean isSensor = false;
        public Array<CollisionFunction> initialBeginCollisions = new Array<>();
        public Array<CollisionFunction> finalBeginCollisions = new Array<>();
        public Array<CollisionFunction> endCollisions = new Array<>();


        public BoneShadowNode setDensity(float density) {
            this.density = density;
            return this;
        }

        public BoneShadowNode setFriction(float friction) {
            this.friction = friction;
            return this;
        }

        public BoneShadowNode setRestitution(float restitution) {
            this.restitution = restitution;
            return this;
        }

        public BoneShadowNode setRotationD(float rotationD) {
            this.rotationD = rotationD;
            return this;
        }


        public BoneShadowNode setSensor(boolean sensor) {
            this.isSensor = sensor;
            return this;
        }


        public BoneShadowNode addInitialBeginCollision(CollisionFunction function) {
            initialBeginCollisions.add(function);
            return this;
        }

        public BoneShadowNode addFinalBeginCollision(CollisionFunction function) {
            finalBeginCollisions.add(function);
            return this;
        }

        public BoneShadowNode addEndCollision(CollisionFunction function) {
            endCollisions.add(function);
            return this;
        }

    }

}
