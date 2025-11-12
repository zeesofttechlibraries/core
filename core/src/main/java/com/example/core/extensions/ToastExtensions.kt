package com.example.googleadsutils_java.GoogleAds.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

private var activeToast: Toast? = null

/**
 * Shows a toast safely and lifecycle-aware.
 * Cancels automatically when the Activity is destroyed.
 */
fun Context.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    val activity = this as? Activity
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
