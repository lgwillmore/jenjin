package com.binarymonks.jj.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarymonks.jj.JJ;

public class PhysicsWorld {

    public World world;
    public int velocityIterations = 10;
    public int positionIterations = 20;
    public CollisionGroups collisionGroups = new CollisionGroupsBasic();

    boolean inStep = false;

    public PhysicsWorld() {
        world = new World(Vector2.Zero, true);
    }

    public void update() {
        inStep = true;
        double frameDelta = JJ.time.getDelta();
        if (frameDelta > 0) {
            world.step((float) JJ.time.getDelta(), velocityIterations, positionIterations);
        }
        inStep = false;
    }

}
