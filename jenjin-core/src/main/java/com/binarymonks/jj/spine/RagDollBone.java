package com.binarymonks.jj.spine;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.BoneData;
import com.esotericsoftware.spine.Skeleton;

import static com.badlogic.gdx.math.MathUtils.cosDeg;
import static com.badlogic.gdx.math.MathUtils.sinDeg;

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
        if (ragDoll) {
            return spinePart.getParent().physicsroot.position().x;

        }
        return super.getWorldX();
    }

    @Override
    public float getWorldY() {
        if (ragDoll) {
            return spinePart.getParent().physicsroot.position().y;
        }
        return super.getWorldY();
    }

    @Override
    public float getA() {
        if (ragDoll) {
            return cosDeg(spinePart.getParent().physicsroot.rotationR() * MathUtils.radDeg);
        }
        return super.getA();
    }

    @Override
    public float getB() {
        if (ragDoll) {
            return cosDeg(spinePart.getParent().physicsroot.rotationR() * MathUtils.radDeg + 90);
        }
        return super.getB();
    }

    @Override
    public float getC() {
        if (ragDoll) {
            return sinDeg(spinePart.getParent().physicsroot.rotationR() * MathUtils.radDeg);
        }
        return super.getC();
    }

    @Override
    public float getD() {
        if (ragDoll) {
            return sinDeg(spinePart.getParent().physicsroot.rotationR() * MathUtils.radDeg + 90);
        }
        return super.getD();
    }

    public void triggerRagDoll() {
        ragDoll = true;
    }

    public void reverseRagDoll() {
        ragDoll = false;
    }
}
