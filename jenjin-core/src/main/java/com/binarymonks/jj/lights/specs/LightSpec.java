package com.binarymonks.jj.lights.specs;

import com.badlogic.gdx.graphics.Color;

public abstract class LightSpec<CONCRETE extends LightSpec> {

    private final CONCRETE self;

    public LightSpec() {
        self = (CONCRETE) this;
    }

    public String name;
    public int rays = 20;
    public Color color = new Color(0.3f, 0.3f, 0.3f, 0.3f);
    public float reach = 2f;
    public String collisionGroup;

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

    public CONCRETE setCollisionGroup(String collisionGroup) {
        this.collisionGroup = collisionGroup;
        return self;
    }

    public CONCRETE setColor(Color color) {
        this.color = color;
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
