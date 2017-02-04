package com.binarymonks.jj.audio;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;

public class EffectsController {

    ObjectMap<String,SingletonWindow> singletonMap = new ObjectMap<>();
	
	private float volume = 0.2f;
	private boolean mute=false;
	
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	public boolean isMute() {
		return mute;
	}
	public void setMute(boolean mute) {
		this.mute = mute;
	}

    public void addSingletonSound(float singletonTimeWindow,String ... soundpaths){
        SingletonWindow window = new SingletonWindow(singletonTimeWindow);
        for (String soundpath : soundpaths){
            singletonMap.put(soundpath,window);
        }
    }

    public boolean canTriggerSingleton(String soundpath){
        if(!singletonMap.containsKey(soundpath)){
            return true;
        }
        return singletonMap.get(soundpath).elapsed();
    }


    private class SingletonWindow{
        public double window;
        public double lastTrigger=0;

        public SingletonWindow(float window) {
            this.window = window;
        }

        public boolean elapsed(){
            double currentT = JJ.time.getTime();
            boolean elapsed =  (currentT -lastTrigger) > window;
            if(elapsed){
                lastTrigger = currentT;
            }
            return elapsed;
        }


    }
	

}
