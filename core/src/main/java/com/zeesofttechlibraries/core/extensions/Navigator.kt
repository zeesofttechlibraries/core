package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

object Navigator {

    /**
     * Navigate to another Activity from any Context (Activity, Fragment, Service, Application).
     *
     * @param context The context to use for starting the activity.
     * @param destination The destination activity class.
     * @param extras A map of extras to pass to the activity.
     *
     * Usage:
     *  Navigator.navigateToActivity(context, MainActivity::class.java)
     *
     * With extras:
     *  Navigator.navigateToActivity(
     *      context,
     *      MainActivity::class.java,
     *      mapOf("userId" to 42, "isLoggedIn" to true)
     *  )
     */
    @JvmStatic
    fun navigateToActivity(context: Context, destination: Class<*>, extras: Map<String, Any>? = null) {
        val intent = Intent(context, destination).apply {
            extras?.let { putExtras(it.toBundle()) }
        }
        if (context !is Activity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    private fun Map<String, Any>.toBundle(): Bundle {
        val bundle = Bundle()
        forEach { (key, value) ->
            when (value) {
                is String -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                is Float -> bundle.putFloat(key, value)
                is Long -> bundle.putLong(key, value)
                is Double -> bundle.putDouble(key, value)
                is CharSequence -> bundle.putCharSequence(key, value)
                is Parcelable -> bundle.putParcelable(key, value)
                is Serializable -> bundle.putSerializable(key, value)
                else -> throw IllegalArgumentException("Unsupported type for key: $key -> ${value::class.java.name}")
            }
        }
        return bundle
    }
}
