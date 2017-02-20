package com.binarymonks.jj.specs.physics.b2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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

    public class Polygon implements B2DShapeSpec {

        public Array<Vector2> edges = new Array<Vector2>();

        public Polygon add(Vector2 vertex) {
            edges.add(vertex);
            return this;
        }

    }

    public static class Chain implements B2DShapeSpec {

        public Array<Vector2> edges = new Array<Vector2>();

        public Chain add(Vector2 vertex) {
            edges.add(vertex);
            return this;
        }
    }
}
