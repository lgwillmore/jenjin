package com.binarymonks.jj.specs.physics;

import com.badlogic.gdx.physics.box2d.BodyDef;

public interface PhysicsRootSpec {

    class Basic implements PhysicsRootSpec {
    }

    class B2D implements PhysicsRootSpec {
        public BodyDef.BodyType bodyType = BodyDef.BodyType.DynamicBody;
        public boolean fixedRotation=false;
        public float linearDamping;
        public float angularDamping;
        public float gravityScale=1;
        public boolean bullet = false;
        public boolean allowSleep = true;

        public B2D setBodyType(BodyDef.BodyType bodyType) {
            this.bodyType = bodyType;
            return this;
        }

        public B2D setFixedRotation(boolean fixedRotation) {
            this.fixedRotation = fixedRotation;
            return this;
        }

        public B2D setLinearDamping(float linearDamping) {
            this.linearDamping = linearDamping;
            return this;
        }

        public B2D setAngularDamping(float angularDamping) {
            this.angularDamping = angularDamping;
            return this;
        }

        public B2D setBullet(boolean bullet) {
            this.bullet = bullet;
            return this;
        }

        public B2D setAllowSleep(boolean allowSleep) {
            this.allowSleep = allowSleep;
            return this;
        }

        public B2D setGravityScale(float gravityScale) {
            this.gravityScale = gravityScale;
            return this;
        }
    }
}
