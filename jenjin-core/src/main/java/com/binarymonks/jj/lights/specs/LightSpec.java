package com.binarymonks.jj.lights.specs;

import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.physics.CollisionDataSpec;
import com.binarymonks.jj.specs.SpecPropField;

public abstract class LightSpec<CONCRETE extends LightSpec> {

    private final CONCRETE self;

    public LightSpec() {
        self = (CONCRETE) this;
    }

    public String name;
    public int rays = 40;
    public SpecPropField<Color, CONCRETE> color = new SpecPropField<>((CONCRETE) this, new Color(0.3f, 0.3f, 0.3f, 1f));
    public float reach = 2f;
    public CollisionDataSpec<LightSpec> collisionData = new CollisionDataSpec<>(this);

    public CONCRETE setName(String name) {
        this.name = name;
        return self;
    }

    public CONCRETE setRays(int rays) {
        this.rays = rays;
        return self;
    }

    public CONCRETE setReach(float reach) {
        this.reach = reach;
        return self;
    }

    public static class Point extends LightSpec<Point> {

        public float offsetX;
        public float offsetY;

        public Point setOffset(float x, float y) {
            this.offsetX = x;
            this.offsetY = y;
            return this;
        }

    }
}
