package com.binarymonks.jj.specs.render;

import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DelegateToPhysics)) return false;
            return true;
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

        @Override
        public String toString() {
            return "Fixed{" +
                    "offsetX=" + offsetX +
                    ", offsetY=" + offsetY +
                    ", rotationD=" + rotationD +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Fixed)) return false;

            Fixed fixed = (Fixed) o;

            if (Float.compare(fixed.offsetX, offsetX) != 0) return false;
            if (Float.compare(fixed.offsetY, offsetY) != 0) return false;
            return Float.compare(fixed.rotationD, rotationD) == 0;

        }

        @Override
        public int hashCode() {
            int result = (offsetX != +0.0f ? Float.floatToIntBits(offsetX) : 0);
            result = 31 * result + (offsetY != +0.0f ? Float.floatToIntBits(offsetY) : 0);
            result = 31 * result + (rotationD != +0.0f ? Float.floatToIntBits(rotationD) : 0);
            return result;
        }
    }
}
