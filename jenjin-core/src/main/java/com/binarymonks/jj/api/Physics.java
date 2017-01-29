package com.binarymonks.jj.api;

import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.physics.CollisionGroups;

public class Physics {

    public void setCollisionGroups(CollisionGroups collisionGroups) {
        Global.physics.collisionGroups = collisionGroups;
    }
}
