package com.binarymonks.jj.spine;

import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.BoneData;
import com.esotericsoftware.spine.Skeleton;

public class RagDollBone extends Bone {

    boolean ragDoll = false;
    SpineBoneComponent spinePart;

    public RagDollBone(BoneData data, Skeleton skeleton, Bone parent) {
        super(data, skeleton, parent);
    }

    public RagDollBone(Bone bone, Skeleton skeleton, Bone parent) {
        super(bone, skeleton, parent);
    }

    @Override
    public float getWorldX() {
        if(ragDoll) {
            return spinePart.getParent().physicsroot.position().x;

        }
        return super.getWorldX();
    }

    @Override
    public float getWorldY() {
        if(ragDoll) {
            return spinePart.getParent().physicsroot.position().y;
        }
        return super.getWorldY();
    }


    public void triggerRagDoll() {
        ragDoll=true;
    }
}
