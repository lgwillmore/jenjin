package com.binarymonks.jj.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.binarymonks.jenjin.JJ;
import com.binarymonks.jenjin.utils.JJMathUtil;

import java.util.List;

/**
 * Created by Laurence on 5/24/2016.
 */
public abstract class SoundPlayer implements Sound {

    SoundEffectParameters parameters;
    String currentSoundPath;

    private SoundPlayer(SoundEffectParameters parameters) {
        this.parameters = parameters;
    }

    public static SoundPlayer getSound(SoundEffectParameters params) {
        if (!params.isBig) {
            return new ShortSound(params);
        }
        return new LongSound(params);
    }

    public void selectRandom() {
        currentSoundPath = selectRandom(parameters.soundPaths);
    }

    private String selectRandom(List<String> soundPaths) {
        int roll = JJMathUtil.randInt(0, soundPaths.size() - 1);
        return soundPaths.get(roll);
    }

    public abstract void triggering();


    public boolean canTriggerSingleton() {
        return JJ.audio.effects.canTriggerSingleton(currentSoundPath);
    }


    private static class ShortSound extends SoundPlayer {

        Sound sound;


        private ShortSound(SoundEffectParameters parameters) {
            super(parameters);
        }

        @Override
        public void triggering() {
            sound = JJ.assetManager.get(currentSoundPath, Sound.class);
        }

        @Override
        public long play() {
            return sound.play();
        }

        @Override
        public long play(float volume) {
            return sound.play(volume);
        }

        @Override
        public long play(float volume, float pitch, float pan) {
            return sound.play(volume, pitch, pan);
        }

        @Override
        public long loop() {
            return sound.loop();
        }

        @Override
        public long loop(float volume) {
            return sound.loop(volume);
        }

        @Override
        public long loop(float volume, float pitch, float pan) {
            return loop(volume, pitch, pan);
        }

        @Override
        public void stop() {
            sound.stop();
            JJ.assetManager.unload(currentSoundPath);
        }

        @Override
        public void pause() {
            sound.pause();
        }

        @Override
        public void resume() {
            sound.resume();
        }

        @Override
        public void dispose() {
            sound.dispose();
        }

        @Override
        public void stop(long soundId) {
            sound.stop(soundId);
            JJ.assetManager.unload(currentSoundPath);
        }

        @Override
        public void pause(long soundId) {
            sound.pause(soundId);
        }

        @Override
        public void resume(long soundId) {
            sound.resume(soundId);
        }

        @Override
        public void setLooping(long soundId, boolean looping) {
            sound.setLooping(soundId, looping);
        }

        @Override
        public void setPitch(long soundId, float pitch) {
            sound.setPitch(soundId, pitch);
        }

        @Override
        public void setVolume(long soundId, float volume) {
            sound.setVolume(soundId, volume);
        }

        @Override
        public void setPan(long soundId, float pan, float volume) {
            sound.setPan(soundId, pan, volume);
        }

//        @Override
//        public void setPriority(long soundId, int priority) {
//            sound.setPriority(soundId, priority);
//        }

    }

    private static class LongSound extends SoundPlayer {

        Music sound;

        public LongSound(SoundEffectParameters parameters) {
            super(parameters);
        }

        @Override
        public void triggering() {
            sound = Gdx.audio.newMusic(Gdx.files.internal(currentSoundPath));
        }


        @Override
        public long play() {
            sound.play();
            return 1;
        }

        @Override
        public long play(float volume) {
            sound.setVolume(volume);
            sound.play();
            return 1;
        }

        @Override
        public long play(float volume, float pitch, float pan) {
            sound.setPan(pan, volume);
            sound.play();
            return 1;
        }

        @Override
        public long loop() {
            sound.setLooping(true);
            sound.play();
            return 1;
        }

        @Override
        public long loop(float volume) {
            sound.setLooping(true);
            sound.setVolume(volume);
            sound.play();
            return 1;
        }

        @Override
        public long loop(float volume, float pitch, float pan) {
            sound.setLooping(true);
            sound.setPan(pan, volume);
            sound.play();
            return 1;
        }

        @Override
        public void stop() {
            sound.stop();
        }

        @Override
        public void pause() {
            sound.pause();
        }

        @Override
        public void resume() {
            sound.play();
        }

        @Override
        public void dispose() {
            sound.dispose();
        }

        @Override
        public void stop(long soundId) {
            sound.stop();
        }

        @Override
        public void pause(long soundId) {
            sound.pause();
        }

        @Override
        public void resume(long soundId) {
            sound.play();
        }

        @Override
        public void setLooping(long soundId, boolean looping) {
            sound.setLooping(looping);
        }

        @Override
        public void setPitch(long soundId, float pitch) {
        }

        @Override
        public void setVolume(long soundId, float volume) {
            sound.setVolume(volume);
        }

        @Override
        public void setPan(long soundId, float pan, float volume) {
            sound.setPan(pan, volume);
        }

//        @Override
//        public void setPriority(long soundId, int priority) {
//
//        }

    }

}
