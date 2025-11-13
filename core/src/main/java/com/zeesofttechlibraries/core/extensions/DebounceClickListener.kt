package com.zeesofttechlibraries.core.extensions

import android.os.SystemClock
import android.view.View
import com.zeesofttechlibraries.core.R

object DebounceClickListener {

    private object GlobalDebounceTracker {
        var lastClickTime = 0L
    }

    /**
     * Debounced click listener.
     * By default, debounce is per view. If [global]=true, debounce is global across all views.
     *
     * - View disabling is based on [debounceTime] or [delaySeconds]
     * - Alpha flickers for 500 ms only (visual feedback), independent of debounce duration
     *
     * @param view The view to set the click listener on.
     * @param debounceTime The debounce time in milliseconds.
     * @param disabledAlpha → Alpha during feedback animation
     * @param enabledAlpha → Alpha restored after animation
     * @param delaySeconds The debounce time in seconds. If provided, it overrides [debounceTime].
     * @param global If true, debounce applies across all views; if false, per-view debounce
     * @param action The click action to perform.
     *
     * Usage:
     *   DebounceClickListener.setDebouncedClickListener(view, delaySeconds = 2) { ... }
     */
    @JvmStatic
    fun setDebouncedClickListener(
        view: View,
        debounceTime: Long = 1000L,
        disabledAlpha: Float = 0.5f,
        enabledAlpha: Float = 1.0f,
        delaySeconds: Long? = null,
        global: Boolean = true,
        action: (View) -> Unit
    ) {
        val debounceMillis = delaySeconds?.let { it * 1000 } ?: debounceTime
        val alphaAnimDuration = 500L // fixed visible feedback duration

        view.setOnClickListener { v ->
            if (!v.isEnabled) return@setOnClickListener

            val now = SystemClock.elapsedRealtime()
            val lastClickTime = if (global) {
                GlobalDebounceTracker.lastClickTime
            } else {
                v.getTag(R.id.debounce_click_time_tag) as? Long ?: 0L
            }

            // Debounce check
            if (now - lastClickTime < debounceMillis) return@setOnClickListener

            // Update last click time
            if (global) {
                GlobalDebounceTracker.lastClickTime = now
            } else {
                v.setTag(R.id.debounce_click_time_tag, now)
            }

            // Visual alpha tap feedback
            v.animate()
                .alpha(disabledAlpha)
                .setDuration(alphaAnimDuration / 2)
                .withEndAction {
                    v.animate()
                        .alpha(enabledAlpha)
                        .setDuration(alphaAnimDuration / 2)
                        .start()
                }
                .start()

            // Prevent rapid taps
            v.isEnabled = false

            try {
                action(v)
            } catch (e: Exception) {
                v.isEnabled = true
                throw e
            }

            v.postDelayed({
                v.isEnabled = true
            }, debounceMillis)
        }
    }
}
