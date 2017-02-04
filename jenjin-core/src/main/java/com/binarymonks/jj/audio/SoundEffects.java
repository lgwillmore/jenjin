package com.binarymonks.jj.audio;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Disposable;
import com.binarymonks.jj.JJ;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundEffects implements Disposable {

    private static final double REPEAT_ELAPSED_TIME = 0.1;
    Map<String, SoundPlayer> sounds = new HashMap<>();
    Map<String, SoundParams> parameters = new HashMap<>();
    String currentSoundId;
    SoundPlayer currentSound;
    long currentSoundPlayID;
    double currentSoundStart;
    boolean stopOnTrigger = false;
    private String currentSoundfxID;

    @Override
    public void dispose() {
        if (currentSound != null) {
            currentSound.stop(currentSoundPlayID);
        }
    }

    private void stopCurrentSound() {
        if (currentSound != null) {
            if (stopOnTrigger) {
                currentSound.stop(currentSoundPlayID);
            }
        }
    }

    public void stopSound() {
        if (currentSound != null) {
            currentSound.stop();
        }
    }

    public void addSoundEffect(SoundParams soundfx) {
        if (soundfx.soundPaths != null) {
            sounds.put(soundfx.id, SoundPlayer.getSound(soundfx));
            parameters.put(soundfx.id, soundfx);
        }
    }

    public void addSoundEffects(List<SoundParams> soundfx) {
        for (SoundParams fxParams : soundfx) {
            addSoundEffect(fxParams);
        }
    }

    public void triggerSound(String soundfxID, SoundMode mode) {
        double elapsed = JJ.time.getTime() - currentSoundStart;
        if (currentSound == null ||
                !currentSoundfxID.equals(soundfxID) || (elapsed > REPEAT_ELAPSED_TIME)) {
            currentSoundId = soundfxID;
            trigger(soundfxID, mode);
        }
    }

    private void trigger(String soundfxID, SoundMode mode) {
        if (sounds.containsKey(soundfxID)) {
            SoundPlayer proposedSound = sounds.get(soundfxID);
            proposedSound.selectRandom();
            if (proposedSound.canTriggerSingleton()) {
                stopCurrentSound();
                currentSoundfxID = soundfxID;
                currentSound = proposedSound;
                currentSound.triggering();
                currentSoundStart = JJ.time.getTime();
                float volume = (JJ.audio.effects.isMute()) ? 0.0f : currentSound.parameters.volume
                        * JJ.audio.effects.getVolume();
                currentSoundPlayID = currentSound.play(volume);
                if (mode.equals(SoundMode.LOOP)) {
                    currentSound.setLooping(currentSoundPlayID, true);
                }
                currentSound.setPitch(currentSoundPlayID,
                        (float) JJ.time.getRealToGameRatio());
            }
        }
    }

    public Map<String, SoundParams> getParams() {
        return parameters;
    }

    public boolean isStopOnTrigger() {
        return stopOnTrigger;
    }

    public void setStopOnTrigger(boolean stopOnTrigger) {
        this.stopOnTrigger = stopOnTrigger;
    }

}
