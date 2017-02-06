package com.binarymonks.jj.physics.specs.b2d;

public interface B2DShapeSpec {

    public static class PolygonRectangle implements B2DShapeSpec {
        public float width = 1;
        public float height = 1;

        public PolygonRectangle(float width, float height) {
            this.width = width;
            this.height = height;
        }

    }

    public static class Circle implements B2DShapeSpec {
        public float radius;

        public Circle(float radius) {
            this.radius = radius;
        }
    }

}
