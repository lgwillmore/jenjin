package com.binarymonks.jj.physics;

public class CollisionDataSpec<OWNER> {

    short category = Short.MAX_VALUE;
    short mask = Short.MAX_VALUE;
    String name;
    OWNER self;

    public CollisionDataSpec(OWNER self) {
        this.self = self;
    }

    public OWNER setToGroup(String name) {
        this.name = name;
        return self;
    }

    public OWNER setToExplicit(short category, short mask) {
        this.category = category;
        this.mask = mask;
        this.name = null;
        return self;
    }

    public OWNER setToExplicit(CollisionGroups.CollisionData collisionData) {
        this.category = collisionData.category;
        this.mask = collisionData.mask;
        this.name = null;
        return self;
    }
}
