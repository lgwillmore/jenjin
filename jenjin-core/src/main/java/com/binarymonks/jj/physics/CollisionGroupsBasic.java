package com.binarymonks.jj.physics;


public class CollisionGroupsBasic extends CollisionGroups {


    protected CollisionGroupData collideWithNone = new CollisionGroupData(Short.MAX_VALUE, Short.MAX_VALUE);

    @Override
    public CollisionGroupData getGroupData(String group) {
        return collideWithNone;
    }

}
