package com.binarymonks.jj.specs.lights;

import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.physics.CollisionDataSpec;
import com.binarymonks.jj.specs.SpecPropField;

public abstract class LightSpec {

    public String name;
    public int rays = 40;
    public SpecPropField<Color> color = new SpecPropField<>(new Color(0.3f, 0.3f, 0.3f, 1f));
    public float reach = 2f;
    public CollisionDataSpec collisionData = new CollisionDataSpec();

    public void setName(String name) {
        this.name = name;
    }

    public void setRays(int rays) {
        this.rays = rays;
    }

    public void setReach(float reach) {
        this.reach = reach;
    }

    public static class Point extends LightSpec {

        public float offsetX;
        public float offsetY;

        public Point setOffset(float x, float y) {
            this.offsetX = x;
            this.offsetY = y;
            return this;
        }

    }
}
