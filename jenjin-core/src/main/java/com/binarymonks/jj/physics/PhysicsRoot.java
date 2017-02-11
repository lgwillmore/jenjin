package com.binarymonks.jj.physics;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;
import com.binarymonks.jj.pools.N;

public interface PhysicsRoot {
    Vector2 position();

    float rotationR();

    /**
     * Be careful if you are using Box2D physics.
     * Rather apply forces through {@link PhysicsRoot#getB2DBody()}
     *
     * @param x
     * @param y
     */
    void setPosition(float x, float y);

    /**
     * Be careful if you are using Box2D physics.
     * Rather apply forces through {@link PhysicsRoot#getB2DBody()}
     *
     * @param rotation
     */
    void setRotationR(float rotation);

    Body getB2DBody();

    Transform getTransform();

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

        @Override
        public void setPosition(float x, float y) {
            body.setTransform(x, y, body.getAngle());
        }

        @Override
        public void setRotationR(float rotation) {
            Vector2 position = body.getPosition();
            body.setTransform(position.x, position.y, rotation);
        }

        @Override
        public Body getB2DBody() {
            return body;
        }

        @Override
        public Transform getTransform() {
            return body.getTransform();
        }
    }

    public static class BasicPhysics implements PhysicsRoot {
        Vector2 position = N.ew(Vector2.class);
        float rotationR = 0;
        Transform transform = new Transform();

        @Override
        public Vector2 position() {
            return position;
        }

        @Override
        public float rotationR() {
            return rotationR;
        }

        @Override
        public void setPosition(float x, float y) {
            position.set(x, y);
        }

        @Override
        public void setRotationR(float rotation) {
            rotationR = rotation;
        }

        @Override
        public Body getB2DBody() {
            return null;
        }

        @Override
        public Transform getTransform() {
            transform.setPosition(position);
            transform.setRotation(rotationR);
            return transform;
        }
    }
}
