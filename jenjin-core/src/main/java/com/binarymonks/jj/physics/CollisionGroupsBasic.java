package com.binarymonks.jj.physics;


public class CollisionGroupsBasic extends CollisionGroups {


    protected CollisionGroupData collideWithNone = new CollisionGroupData((short) 0, (short) 0);

    @Override
    public CollisionGroupData getGroupData(String group) {
        return collideWithNone;
    }

}
