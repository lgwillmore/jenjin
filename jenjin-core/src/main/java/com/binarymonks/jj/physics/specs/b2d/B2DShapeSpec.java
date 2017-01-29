package com.binarymonks.jj.physics.specs.b2d;

public interface B2DShapeSpec {

    public static class PolygonSquare implements B2DShapeSpec {
        public float width = 1;
        public float height = 1;

        public PolygonSquare(float width, float height) {
            this.width = width;
            this.height = height;
        }
    }

}
