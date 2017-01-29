package com.binarymonks.jj.api;

import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.physics.CollisionGroups;

public interface Physics {

    void setCollisionGroups(CollisionGroups collisionGroups);

    void addPostPhysicsFunction(Function function);
}
