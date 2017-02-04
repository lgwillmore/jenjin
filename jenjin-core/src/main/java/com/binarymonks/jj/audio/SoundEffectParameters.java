package com.binarymonks.jj.audio;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import java.util.ArrayList;
import java.util.List;

public class SoundEffectParameters {

	public List<String> soundPaths = new ArrayList<String>();
	public String id;
	public float volume = 1.0f;
	public PlayMode playMode = PlayMode.NORMAL;
    public boolean isBig = false;

}
