package com.binarymonks.jj.physics;

public class Material {

    public static Material WOOD_LIGHT = new Material(0.3f,0.05f,0.4f);
    public static Material WOOD_HEAVY = new Material(0.3f,0.05f,0.5f);
    public static Material STONE_HEAVY = new Material(0.2f,0.0f,0.7f);
    public static Material STONE_LIGHT = new Material(0.2f,0.0f,0.6f);
    public static Material METAL_HEAVY = new Material(0.1f,0.0f,0.9f);
    public static Material METAL_LIGHT = new Material(0.1f,0.0f,0.8f);

    public Material(float friction, float restitution, float density) {
        this.friction = friction;
        this.restitution = restitution;
        this.density = density;
    }

    public float friction;
    public float restitution;
    public float density;

}
