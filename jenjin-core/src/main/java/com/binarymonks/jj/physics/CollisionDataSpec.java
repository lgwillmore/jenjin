package com.binarymonks.jj.physics;

import com.binarymonks.jj.physics.CollisionGroups;

public class CollisionDataSpec {

    short category = Short.MAX_VALUE;
    short mask = Short.MAX_VALUE;
    String name;

    public void setToGroup(String name) {
        this.name = name;
    }

    public void setToExplicit(short category, short mask) {
        this.category = category;
        this.mask = mask;
        this.name = null;
    }

    public void setToExplicit(CollisionGroups.CollisionData collisionData) {
        this.category = collisionData.category;
        this.mask = collisionData.mask;
        this.name = null;
    }
}
