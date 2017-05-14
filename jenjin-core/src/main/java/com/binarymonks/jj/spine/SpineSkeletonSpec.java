package com.binarymonks.jj.spine;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.physics.CollisionDataSpec;
import com.binarymonks.jj.physics.CollisionFunction;
import com.binarymonks.jj.specs.physics.b2d.FixtureNodeSpec;

public class SpineSkeletonSpec {

    ObjectMap<String, FixtureNodeSpec> boneOverrides = new ObjectMap<>();
    ObjectMap<String, BoneAppendSpec> boneAppendices = new ObjectMap<>();
    public EveryBone everyBone = new EveryBone(this);

    public SpineSkeletonSpec setBoneOverride(String boneName, FixtureNodeSpec fixtureNodeSpec) {
        boneOverrides.put(boneName, fixtureNodeSpec);
        return this;
    }

    public SpineSkeletonSpec setBoneAppendix(String boneName, BoneAppendSpec boneAppendSpec) {
        this.boneAppendices.put(boneName, boneAppendSpec);
        return this;
    }



    public static class EveryBone{
        public float boneWidth = 0.1f;
        public CollisionDataSpec collisionData = new CollisionDataSpec();
        public float coreMass = 0.4f;
        public float massFallOff = 0.35f;
        public boolean isSensor = false;
        public CollisionFunction collisionFunction;
        public BoneAppendSpec append;
        SpineSkeletonSpec parent;

        public EveryBone(SpineSkeletonSpec parent) {
            this.parent = parent;
        }

        public SpineSkeletonSpec setBoneWidth(float boneWidth) {
            this.boneWidth = boneWidth;
            return parent;
        }

        public SpineSkeletonSpec setCollisionData(CollisionDataSpec collisionData) {
            this.collisionData = collisionData;
            return parent;
        }

        public SpineSkeletonSpec setCoreMass(float coreMass) {
            this.coreMass = coreMass;
            return parent;
        }

        public SpineSkeletonSpec setMassFallOff(float massFallOff) {
            this.massFallOff = massFallOff;
            return parent;
        }

        public SpineSkeletonSpec setSensor(boolean sensor) {
            isSensor = sensor;
            return parent;
        }

        public SpineSkeletonSpec setAllAppend(BoneAppendSpec boneAppendSpec){
            append =boneAppendSpec;
            return parent;
        }

        public SpineSkeletonSpec setCollisionFunction(CollisionFunction collisionFunction) {
            this.collisionFunction = collisionFunction;
            return parent;
        }

    }
}
