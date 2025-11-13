package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

object ToastManager {
    private var activeToast: Toast? = null

    /**
     * Shows a toast safely and lifecycle-aware.
     * Cancels automatically when the Activity is destroyed.
     *
     * @param context The context to use for showing the toast.
     * @param message The message to show in the toast.
     * @param duration The duration for which the toast should be shown.
     */
    @JvmStatic
    fun showToast(context: Context, message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        val activity = context as? Activity
        if (activity == null || activity.isFinishing || activity.isDestroyed) return

        // Cancel previous toast if visible
        activeToast?.cancel()

        val toast = Toast.makeText(activity.applicationContext, message, duration)
        activeToast = toast
        toast.show()

        // Observe lifecycle to auto-cancel
        if (activity is LifecycleOwner) {
            activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    activeToast?.cancel()
                    activeToast = null
                    activity.lifecycle.removeObserver(this)
                }
            })
        }
    }
}
