package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable

/**
 * Navigate from a Fragment to another Activity.
 *
 * Automatically adds FLAG_ACTIVITY_NEW_TASK if called outside an Activity context.
 * Can pass optional extras as a Map<String, Any>.
 *
 * Usage:
 *  fragment.navigateToActivity(MainActivity::class.java)
 *
 * With extras:
 *  fragment.navigateToActivity(
 *      MainActivity::class.java,
 *      mapOf("userId" to 42, "isLoggedIn" to true)
 *  )
 */
fun Fragment.navigateToActivity(destination: Class<*>, extras: Map<String, Any>? = null) {
    val intent = Intent(requireContext(), destination).apply {
        extras?.let { putExtras(it.toBundle()) }
    }

    if (requireContext() !is Activity) {
        // Add FLAG_ACTIVITY_NEW_TASK when not called from an Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    startActivity(intent)
}

/**
 * Converts a Map<String, Any> into a Bundle safely.
 *
 * Supported types:
 *  - String, Int, Boolean, Float, Long, Double
 *  - CharSequence, Parcelable, Serializable
 *
 * @throws IllegalArgumentException if an unsupported type is passed
 *
 * Example:
 *  val extras = mapOf("id" to 10, "name" to "Hasnat")
 *  val bundle = extras.toBundle()
 */
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
            else -> throw IllegalArgumentException(
                "Unsupported type for key: $key -> ${value::class.java.name}"
            )
        }
    }
    return bundle
}
