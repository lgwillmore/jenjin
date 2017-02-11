package com.binarymonks.jj.render.specs;

import com.binarymonks.jj.physics.specs.PhysicsNodeSpec;

public interface Spatial {

    float getOffsetX(PhysicsNodeSpec nodeSpec);

    float getOffsetY(PhysicsNodeSpec nodeSpec);

    float getRotationD(PhysicsNodeSpec nodeSpec);

    class DelegateToPhysics implements Spatial {

        @Override
        public float getOffsetX(PhysicsNodeSpec nodeSpec) {
            return nodeSpec.getOffsetX();
        }

        @Override
        public float getOffsetY(PhysicsNodeSpec nodeSpec) {
            return nodeSpec.getOffsetY();
        }

        @Override
        public float getRotationD(PhysicsNodeSpec nodeSpec) {
            return nodeSpec.getRotationD();
        }
    }

    class Fixed implements Spatial {
        float offsetX;
        float offsetY;
        float rotationD;

        public Fixed() {
        }

        public Fixed(float offsetX, float offsetY, float rotationD) {
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.rotationD = rotationD;
        }

        public Fixed setOffset(float x, float y) {
            this.offsetX = x;
            this.offsetY = y;
            return this;
        }

        public Fixed setRotationD(float rotationD) {
            this.rotationD = rotationD;
            return this;
        }


        @Override
        public float getOffsetX(PhysicsNodeSpec nodeSpec) {
            return offsetX;
        }

        @Override
        public float getOffsetY(PhysicsNodeSpec nodeSpec) {
            return offsetY;
        }

        @Override
        public float getRotationD(PhysicsNodeSpec nodeSpec) {
            return rotationD;
        }
    }
}
