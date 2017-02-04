package com.binarymonks.jj.audio;

import java.util.ArrayList;
import java.util.List;

public class SoundParams {

    public List<String> soundPaths = new ArrayList<String>();
    public String id;
    public float volume = 1.0f;
    public boolean isBig = false;

    public SoundParams(String id) {
        this.id = id;
    }

    public SoundParams addPath(String path) {
        soundPaths.add(path);
        return this;
    }

    public SoundParams setVolume(float volume) {
        this.volume = volume;
        return this;
    }

    public SoundParams setBig(boolean big) {
        isBig = big;
        return this;
    }
}
