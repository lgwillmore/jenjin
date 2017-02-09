package com.binarymonks.jj.behaviour;

import com.binarymonks.jj.JJ;
import com.binarymonks.jj.specs.PropField;

/**
 * Created by lwillmore on 08/02/17.
 */
public class DestroySelf extends Behaviour {

    public PropField<Float, DestroySelf> timeToLive = new PropField<>(this, 1f);
    int destroySelfTimerID;

    @Override
    public void getReady() {
        destroySelfTimerID = JJ.time.scheduleInSeconds(this::destroyMe, timeToLive.get(), false);
    }

    @Override
    public void doWork() {

    }


    @Override
    public void tearDown() {
        JJ.time.cancelScheduled(destroySelfTimerID);
    }

    private void destroyMe() {
        parent.markForDestruction();
    }

    @Override
    public Behaviour clone() {
        DestroySelf clone = new DestroySelf();
        clone.timeToLive.copyFrom(timeToLive);
        return clone;
    }
}
