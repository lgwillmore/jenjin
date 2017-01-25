package com.binarymonks.jj.render;

import com.badlogic.gdx.math.Vector2;

/**
 * Objects to determine position stuff in world space.
 */
public interface Spatial {
    Vector2 position();

    float rotationD();

    void dispose();

    /****************************************
     *           Implementers Below         *
     ****************************************/
    class ObjectRelative implements Spatial {
        Vector2 localOffset;
        float localRotationD;

        public ObjectRelative(Vector2 localOffset, float localRotationD) {
            this.localOffset = localOffset;
            this.localRotationD = localRotationD;
        }

        @Override
        public Vector2 position() {
            return null;
        }

        @Override
        public float rotationD() {
            return 0;
        }

        @Override
        public void dispose() {

        }
    }
}
