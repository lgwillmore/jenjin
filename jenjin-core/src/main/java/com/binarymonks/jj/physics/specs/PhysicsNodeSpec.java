package com.binarymonks.jj.physics.specs;

/**
 * Created by lwillmore on 25/01/17.
 */
public interface PhysicsNodeSpec {

    float getOffsetX();
    float getOffsetY();
    float getRotationD();


    class Null implements PhysicsNodeSpec {

        @Override
        public float getOffsetX() {
            return 0;
        }

        @Override
        public float getOffsetY() {
            return 0;
        }

        @Override
        public float getRotationD() {
            return 0;
        }
    }
}
