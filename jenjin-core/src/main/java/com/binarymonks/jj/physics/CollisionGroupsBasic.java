package com.binarymonks.jj.physics;


public class CollisionGroupsBasic extends CollisionGroups {

    @Override
    CollisionData getGroupFromName(String group) {
        return EVERYTHING;
    }

}
