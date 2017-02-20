package com.binarymonks.jj.specs.render;

/**
 * Created by lwillmore on 13/02/17.
 */
public class AnimationSequence {

    public String name;
    public float duration;
    public int startFrame;
    public int endFrame;

    public AnimationSequence setName(String name) {
        this.name = name;
        return this;
    }

    public AnimationSequence setDuration(float duration) {
        this.duration = duration;
        return this;
    }

    public AnimationSequence setStartEnd(int startFrame, int endFrame) {
        this.startFrame = startFrame;
        this.endFrame = endFrame;
        return this;
    }

}
