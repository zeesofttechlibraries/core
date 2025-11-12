package com.zeesofttechlibraries.core.extensions

import android.view.animation.Animation
import android.view.animation.RotateAnimation
import kotlin.apply

/**
 * Utility object to create RotateAnimation easily.
 *
 * Provides a dynamic method to customize rotation degrees, pivot, duration, and fill behavior.
 */
object RotateAnimationUtil {

    /**
     * Creates a RotateAnimation with customizable parameters.
     *
     * @param fromDegrees Starting angle in degrees (default 0f)
     * @param toDegrees Ending angle in degrees (default 180f)
     * @param pivotX Pivot X relative to self (default 0.5f)
     * @param pivotY Pivot Y relative to self (default 0.5f)
     * @param durationMs Duration of animation in milliseconds (default 400ms)
     * @param fillAfter Whether the view should retain the end state (default true)
     *
     * @return RotateAnimation instance
     */
    fun getRotateAnimation(
        fromDegrees: Float = 0f,
        toDegrees: Float = 180f,
        pivotX: Float = 0.5f,
        pivotY: Float = 0.5f,
        durationMs: Long = 400L,
        fillAfter: Boolean = true
    ): RotateAnimation {
        return RotateAnimation(
            fromDegrees,
            toDegrees,
            Animation.RELATIVE_TO_SELF,
            pivotX,
            Animation.RELATIVE_TO_SELF,
            pivotY
        ).apply {
            duration = durationMs
            this.fillAfter = fillAfter
        }
    }
}
