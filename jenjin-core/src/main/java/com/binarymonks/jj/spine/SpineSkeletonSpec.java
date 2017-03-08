package com.binarymonks.jj.spine;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.physics.CollisionDataSpec;
import com.binarymonks.jj.specs.physics.b2d.FixtureNodeSpec;

public class SpineSkeletonSpec {

    ObjectMap<String,FixtureNodeSpec> boneOverrides = new ObjectMap<>();
    public float boneWidth = 0.1f;
    public CollisionDataSpec collisionData = new CollisionDataSpec();
    public float coreMass = 0.4f;
    public float massFallOff=0.35f;
    public SpineSkeletonSpec setBoneOverride(String boneName, FixtureNodeSpec fixtureNodeSpec){
        boneOverrides.put(boneName,fixtureNodeSpec);
        return this;
    }
}
