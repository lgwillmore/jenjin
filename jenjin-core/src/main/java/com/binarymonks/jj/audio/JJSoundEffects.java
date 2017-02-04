package com.binarymonks.jj.audio;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Disposable;
import com.binarymonks.jj.JJ;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JJSoundEffects implements Disposable {

    private static final double REPEAT_ELAPSED_TIME = 0.1;
    Map<String, SoundPlayer> sounds = new HashMap<>();
    Map<String, SoundEffectParameters> parameters = new HashMap<>();
    String currentSoundId;
    String defaultSound;
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

    public void stopSound(){
        if(currentSound != null){
            currentSound.stop();
        }
    }

    public void addSoundEffect(SoundEffectParameters soundfx) {
        if (soundfx.soundPaths != null) {
            sounds.put(soundfx.id, SoundPlayer.getSound(soundfx));
            parameters.put(soundfx.id,soundfx);
        }
    }

    public void addSoundEffects(List<SoundEffectParameters> soundfx) {
        for (SoundEffectParameters fxParams : soundfx) {
            addSoundEffect(fxParams);
        }
    }

    public void triggerSound(String soundfxID) {
        double elapsed = JJ.time.getTime() - currentSoundStart;
        if (currentSound == null ||
                !currentSoundfxID.equals(soundfxID) || (elapsed > REPEAT_ELAPSED_TIME)) {
            currentSoundId = soundfxID;
            trigger(soundfxID);
        }
    }

    private void trigger(String soundfxID) {
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
                if (currentSound.parameters.playMode.name().startsWith(
                        PlayMode.LOOP.name())) {
                    currentSound.setLooping(currentSoundPlayID, true);
                }
                currentSound.setPitch(currentSoundPlayID,
                        (float) JJ.time.getRealToGameRatio());
            }
        }
    }



    public void setDefaultSound(String soundID) {
        defaultSound = soundID;
        triggerSound(defaultSound);
    }

    public Map<String, SoundEffectParameters> getParams() {
        return parameters;
    }

    public boolean isStopOnTrigger() {
        return stopOnTrigger;
    }

    public void setStopOnTrigger(boolean stopOnTrigger) {
        this.stopOnTrigger = stopOnTrigger;
    }

}
