package com.binarymonks.jj.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public interface PhysicsRoot {
    Vector2 position();
    float rotationR();

    public static class B2DPhysicsRoot implements PhysicsRoot {
        Body body;

        public B2DPhysicsRoot(Body body) {
            this.body = body;
        }

        @Override
        public Vector2 position() {
            return body.getPosition();
        }

        @Override
        public float rotationR() {
            return body.getAngle();
        }
    }
}
