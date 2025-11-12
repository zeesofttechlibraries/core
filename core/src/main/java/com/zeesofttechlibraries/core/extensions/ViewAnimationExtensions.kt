package com.zeesofttechlibraries.core.extensions

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation

/**
 * Smoothly fades in the view.
 *
 * @param duration Duration of the animation in milliseconds (default = 300)
 */
fun View.fadeIn(duration: Long = 300) {
    this.visibility = View.VISIBLE
    val fadeIn = AlphaAnimation(0f, 1f).apply {
        this.duration = duration
        fillAfter = true
    }
    this.startAnimation(fadeIn)
}

/**
 * Smoothly fades out the view.
 *
 * @param duration Duration of the animation in milliseconds (default = 300)
 */
fun View.fadeOut(duration: Long = 300) {
    val fadeOut = AlphaAnimation(1f, 0f).apply {
        this.duration = duration
        fillAfter = true
    }
    this.startAnimation(fadeOut)
    this.visibility = View.GONE
}

/**
 * Slides the view upward into visibility.
 *
 * @param duration Duration of the animation in milliseconds (default = 300)
 */
fun View.slideUp(duration: Long = 300) {
    this.visibility = View.VISIBLE
    val slideUp = TranslateAnimation(
        0f, 0f, this.height.toFloat(), 0f
    ).apply {
        this.duration = duration
        fillAfter = true
    }
    this.startAnimation(slideUp)
}

/**
 * Slides the view downward out of visibility.
 *
 * @param duration Duration of the animation in milliseconds (default = 300)
 */
fun View.slideDown(duration: Long = 300) {
    val slideDown = TranslateAnimation(
        0f, 0f, 0f, this.height.toFloat()
    ).apply {
        this.duration = duration
        fillAfter = true
    }
    this.startAnimation(slideDown)
    this.visibility = View.GONE
}
