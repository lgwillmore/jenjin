package com.binarymonks.jj.api;

import com.binarymonks.jj.audio.EffectsController;
import com.binarymonks.jj.audio.MusicController;

public class Audio {
	public static int MAX_SIMULTANEOUS_SOUNDS = 100;
	public MusicController music = new MusicController();
	public EffectsController effects = new EffectsController();
}
