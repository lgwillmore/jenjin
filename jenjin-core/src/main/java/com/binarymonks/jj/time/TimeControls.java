package com.binarymonks.jj.time;

import com.badlogic.gdx.Gdx;
import com.binarymonks.jj.api.Time;
import com.binarymonks.jj.async.Function;

public class TimeControls implements Time {
    private static double fixedDelta = 1f / 60;
    private static float fixedDeltaFloat = 1f / 60;
    private static float realToGameRatio = 1.0f;
    private static double DELTA = 1f / 60;
    private static float DELTA_FLOAT = 1f / 60;
    private static double TIME = 0;
    private static TimeFunction timeFunction = TimeFunction.RATIO_TIME;
    private Scheduler scheduler = new Scheduler(this::getTime);


    @Override
    public double getDelta() {
        return DELTA;
    }

    @Override
    public float getDeltaFloat() {
        return DELTA_FLOAT;
    }

    public void update() {
        timeFunction.update(Gdx.graphics.getDeltaTime());
        TIME += DELTA;
        scheduler.update();
    }

    @Override
    public void setTimeFunction(TimeFunction function) {
        timeFunction = function;
    }

    @Override
    public double getTime() {
        return TIME;
    }

    @Override
    public double getFixedDelta() {
        return fixedDelta;
    }

    @Override
    public double getRatioFixedDelta() {
        return fixedDelta * realToGameRatio;
    }

    @Override
    public void setFixedDelta(double fixedTime) {
        TimeControls.fixedDelta = fixedTime;
        TimeControls.fixedDeltaFloat = new Double(fixedTime).floatValue();
    }

    @Override
    public double getRealToGameRatio() {
        return realToGameRatio;
    }

    @Override
    public void setRealToGameRatio(float realToGameRatio) {
        TimeControls.realToGameRatio = realToGameRatio;
    }

    @Override
    public TimeFunction getTimeFunction() {
        return timeFunction;
    }

    @Override
    public int scheduleInSeconds(Function function, float seconds, boolean repeat) {
        return scheduler.scheduleInSeconds(function, seconds, repeat);
    }

    @Override
    public void cancelScheduled(int scheduleID) {

    }

    public enum TimeFunction {
        REAL_TIME {
            @Override
            public void update(float realDelta) {
                DELTA = realDelta;
                DELTA_FLOAT = realDelta;
            }

        },
        FIXED_TIME {
            @Override
            public void update(float realDelta) {
                DELTA = fixedDelta;
                DELTA_FLOAT = fixedDeltaFloat;
            }

        },
        RATIO_TIME {
            @Override
            public void update(float realDelta) {
                DELTA = realToGameRatio * realDelta;
                DELTA_FLOAT = realToGameRatio * realDelta;
            }

        };

        public abstract void update(float realDelta);
    }
}
