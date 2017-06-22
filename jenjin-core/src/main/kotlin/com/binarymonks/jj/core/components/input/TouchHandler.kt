package com.binarymonks.jj.core.components.input

import com.binarymonks.jj.core.components.Component
import kotlin.reflect.KClass


abstract class TouchHandler : Component() {

    /**
     * If true - the scene Scene will be tracked with the touch.
     * So drag [onTouchMove] and [onTouchUp] will be called
     * unless something interrupts the tracking.
     *
     * If false - the follow on events will not be triggered.
     */
    var trackTouch = true

    /**
     * If true - the touch coordinates will be passed with the offset of the touch
     * in local space. ie. if you move the body to this position the body will retain its
     * position relative to the local body coordinates of the touch
     */
    var relativeToTouch = true
    

    /**
     * Return true if the touch event is handled and should not be propagated further,
     * false otherwise.
     */
    abstract fun onTouchDown(touchX: Float, touchY: Float, button: Int): Boolean

    abstract fun onTouchMove(touchX: Float, touchY: Float, button: Int)

    abstract fun onTouchUp(touchX: Float, touchY: Float, button: Int)

    override fun type(): KClass<Component> {
        @Suppress("unchecked_cast")
        return TouchHandler::class as KClass<Component>
    }
}