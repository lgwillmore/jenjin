package com.binarymonks.jj.core.time

import com.badlogic.gdx.Gdx
import com.binarymonks.jj.core.api.TimeAPI

class TimeControls : TimeAPI {

    override val delta: Double
        get() = DELTA

    override val deltaFloat: Float
        get() = DELTA_FLOAT

    override val time: Double
        get() = TIME

    fun update() {
        timeFunction.update(Gdx.graphics.deltaTime)
        TIME += DELTA
    }

    fun setTimeFunction(function: TimeFunction) {
        timeFunction = function
    }

    fun getFixedDelta(): Double {
        return fixedDelta
    }

    val ratioFixedDelta: Double
        get() = fixedDelta * realToGameRatio

    fun setFixedDelta(fixedTime: Double) {
        TimeControls.fixedDelta = fixedTime
        TimeControls.fixedDeltaFloat = fixedTime.toFloat()
    }

    fun getRealToGameRatio(): Double {
        return realToGameRatio.toDouble()
    }

    fun setRealToGameRatio(realToGameRatio: Float) {
        TimeControls.realToGameRatio = realToGameRatio
    }

    fun getTimeFunction(): TimeFunction {
        return timeFunction
    }

    enum class TimeFunction {
        REAL_TIME {
            override fun update(realDelta: Float) {
                DELTA = realDelta.toDouble()
                DELTA_FLOAT = realDelta
            }

        },
        FIXED_TIME {
            override fun update(realDelta: Float) {
                DELTA = fixedDelta
                DELTA_FLOAT = fixedDeltaFloat
            }

        },
        RATIO_TIME {
            override fun update(realDelta: Float) {
                DELTA = (realToGameRatio * realDelta).toDouble()
                DELTA_FLOAT = realToGameRatio * realDelta
            }

        };

        abstract fun update(realDelta: Float)
    }

    companion object {
        private var fixedDelta = (1f / 60).toDouble()
        private var fixedDeltaFloat = 1f / 60
        private var realToGameRatio = 1.0f
        private var DELTA = (1f / 60).toDouble()
        private var DELTA_FLOAT = 1f / 60
        private var TIME = 0.0
        private var timeFunction = TimeFunction.RATIO_TIME
    }
}
