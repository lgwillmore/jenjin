package com.binarymonks.jj.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class MusicController implements Music.OnCompletionListener {
    private float volume = 0.2f;
    private boolean mute = false;
    String musicPath;
    Music music;
    boolean musicFinished = true;

    public void setMusic(String musicPath) {
        disposeCurrentMusic();
        this.musicPath = musicPath;
        music = Gdx.audio.newMusic(Gdx.files.internal(musicPath));
        music.setVolume(calculateVolume());
        music.setOnCompletionListener(this);
    }

    private float calculateVolume() {
        return (mute) ? 0.0f : volume;
    }

    public void play() {
        musicFinished=false;
        music.play();
    }

    public void playLoop() {
        music.setLooping(true);
        play();
    }

    public boolean isMusicPlaying() {
        return music.isPlaying();
    }

    public boolean isMusicFinished(){
        return musicFinished;
    }

    public void stop() {
        music.stop();
    }

    public void disposeCurrentMusic() {
        if (music != null) {
            music.stop();
            music.dispose();
        }
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (music != null) {
            music.setVolume(calculateVolume());
        }
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
        if (music != null) {
            music.setVolume(calculateVolume());
        }
    }

    @Override
    public void onCompletion(Music music) {
        musicFinished=true;
    }
}
