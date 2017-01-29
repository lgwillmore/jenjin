package com.binarymonks.jj.api;

import com.binarymonks.jj.time.TimeControls;

public interface Time {

    double getDelta();

    float getDeltaFloat();

    void setTimeFunction(TimeControls.TimeFunction function);

    double getTime();

    double getFixedDelta();

    double getRatioFixedDelta();

    void setFixedDelta(double fixedTime);

    double getRealToGameRatio();

    void setRealToGameRatio(float realToGameRatio);

    TimeControls.TimeFunction getTimeFunction();

}
