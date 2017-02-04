package com.binarymonks.jj.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.api.Physics;
import com.binarymonks.jj.async.Function;

public class PhysicsWorld implements Physics {

    public World world;
    public int velocityIterations = 10;
    public int positionIterations = 20;
    public CollisionGroups collisionGroups = new CollisionGroupsBasic();
    Array<Function> postPhysicsFunctions = new Array<>();

    boolean updating = false;

    public PhysicsWorld() {
        world = new World(Vector2.Zero, true);
        world.setContactListener(new JJContactListener());
    }

    public void update() {
        updating = true;
        double frameDelta = JJ.time.getDelta();
        if (frameDelta > 0) {
            world.step((float) JJ.time.getDelta(), velocityIterations, positionIterations);
        }
        for (Function function : postPhysicsFunctions) {
            function.call();
        }
        postPhysicsFunctions.clear();
        updating = false;
    }

    public boolean isUpdating() {
        return updating;
    }

    @Override
    public void setCollisionGroups(CollisionGroups collisionGroups) {
        this.collisionGroups = collisionGroups;
    }

    @Override
    public void addPostPhysicsFunction(Function function) {
        postPhysicsFunctions.add(function);
    }
}
