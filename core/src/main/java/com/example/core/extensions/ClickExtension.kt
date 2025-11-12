package com.example.googleadsutils_java.GoogleAds.extensions

import android.os.SystemClock
import android.view.View
import com.example.core.R

private object GlobalDebounceTracker {
    var lastClickTime = 0L
}

/**
 * Debounced click extension.
 *
 * Only `delaySeconds` is used to control debounce timing.
 *
 * @param delaySeconds → Debounce delay in seconds (default = 1 second)
 * @param disabledAlpha → Alpha during feedback animation
 * @param enabledAlpha → Alpha restored after animation
 * @param global → If true, debounce applies across all views; if false, per-view debounce
 *
 * Usage:
 *   view.setDebouncedClickListener(delaySeconds = 2) { ... }
 */
fun View.setDebouncedClickListener(
    delaySeconds: Long = 1L,
    disabledAlpha: Float = 0.5f,
    enabledAlpha: Float = 1.0f,
    global: Boolean = true,
    action: (View) -> Unit
) {
    val debounceMillis = delaySeconds * 1000
    val alphaAnimDuration = 500L // visual tap feedback duration

    setOnClickListener { v ->
        if (!isEnabled) return@setOnClickListener

        val now = SystemClock.elapsedRealtime()
        val lastClickTime = if (global) {
            GlobalDebounceTracker.lastClickTime
        } else {
            getTag(R.id.debounce_click_time_tag) as? Long ?: 0L
        }

        // Debounce check
        if (now - lastClickTime < debounceMillis) return@setOnClickListener

        // Update last click time
        if (global) {
            GlobalDebounceTracker.lastClickTime = now
        } else {
            setTag(R.id.debounce_click_time_tag, now)
        }

        // Visual alpha tap feedback
        animate()
            .alpha(disabledAlpha)
            .setDuration(alphaAnimDuration / 2)
            .withEndAction {
                animate()
                    .alpha(enabledAlpha)
                    .setDuration(alphaAnimDuration / 2)
                    .start()
            }
            .start()

        // Prevent rapid taps
        isEnabled = false

        try {
            action(v)
        } catch (e: Exception) {
            isEnabled = true
            throw e
        }

        postDelayed({
            isEnabled = true
        }, debounceMillis)
    }
}
