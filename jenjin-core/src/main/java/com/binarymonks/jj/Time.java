package com.binarymonks.jj;

import com.badlogic.gdx.Gdx;

public class Time {
    private static double fixedDelta = 1f / 60;
    private static float fixedDeltaFloat = 1f / 60;
    private static float realToGameRatio = 1.0f;
    private static double DELTA = 1f / 60;
    private static float DELTA_FLOAT = 1f / 60;
    private static double TIME = 0;
    private static TimeFunction timeFunction = TimeFunction.RATIO_TIME;


    public double getDelta() {
        return DELTA;
    }

    public float getDeltaFloat() {
        return DELTA_FLOAT;
    }

    void update() {
        timeFunction.update(Gdx.graphics.getDeltaTime());
        TIME += DELTA;
    }

    public void setTimeFunction(TimeFunction function) {
        timeFunction = function;
    }

    public double getTime() {
        return TIME;
    }

    public double getFixedDelta() {
        return fixedDelta;
    }

    public double getRatioFixedDelta() {
        return fixedDelta * realToGameRatio;
    }

    public void setFixedDelta(double fixedTime) {
        Time.fixedDelta = fixedTime;
        Time.fixedDeltaFloat = new Double(fixedTime).floatValue();
    }

    public double getRealToGameRatio() {
        return realToGameRatio;
    }

    public void setRealToGameRatio(float realToGameRatio) {
        Time.realToGameRatio = realToGameRatio;
    }

    public TimeFunction getTimeFunction() {
        return timeFunction;
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
