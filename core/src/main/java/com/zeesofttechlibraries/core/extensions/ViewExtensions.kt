package com.zeesofttechlibraries.core.extensions

import android.os.SystemClock
import android.view.View
import com.zeesofttechlibraries.core.R
import kotlin.let

private object GlobalDebounceTracker {
    var lastClickTime = 0L
}


/**
 * Debounced click extension.
 * By default, debounce is per view. If [global]=true, debounce is global across all views.
 *
 * - View disabling is based on [debounceTime] or [delaySeconds]
 * - Alpha flickers for 300 ms only (visual feedback), independent of debounce duration
 */
object DebounceClickListener{
    @JvmStatic
    fun setDebouncedClickListener(view: View, action: (View) -> Unit) {
        view.setDebouncedClickListener(action = action)
    }

    @JvmStatic
    fun View.setDebouncedClickListener(
        debounceTime: Long = 1000L,
        disabledAlpha: Float = 0.5f,
        enabledAlpha: Float = 1.0f,
        delaySeconds: Long? = null,
        global: Boolean = true,
        action: (View) -> Unit
    ) {
        val debounceMillis = delaySeconds?.let { it * 1000 } ?: debounceTime
        val alphaAnimDuration = 500L // fixed visible feedback duration

        setOnClickListener { v ->
            if (!isEnabled) return@setOnClickListener

            val now = SystemClock.elapsedRealtime()
            val lastClickTime = if (global) {
                GlobalDebounceTracker.lastClickTime
            } else {
                getTag(R.id.debounce_click_time_tag) as? Long ?: 0L
            }

            if (global && now - lastClickTime < debounceMillis) return@setOnClickListener

            if (global) {
                GlobalDebounceTracker.lastClickTime = now
            } else {
                setTag(R.id.debounce_click_time_tag, now)
            }

            // 🔹 Short alpha animation for visual click feedback (not the full disable period)
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

            // 🔹 Disable logic for full debounce time
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
}