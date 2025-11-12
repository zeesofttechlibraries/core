package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * Navigate to another Activity from any Context (Activity, Fragment, Service, Application).
 *
 * Usage:
 *  navigateToActivity(MainActivity::class.java)
 *
 * With extras:
 *  navigateToActivity(
 *      MainActivity::class.java,
 *      mapOf("userId" to 42, "isLoggedIn" to true)
 *  )
 *
 * Notes:
 * - Automatically adds FLAG_ACTIVITY_NEW_TASK if called outside an Activity.
 * - Works seamlessly inside Fragments by using requireContext().navigateToActivity(...)
 */
fun Context.navigateToActivity(destination: Class<*>, extras: Map<String, Any>? = null) {
    val intent = Intent(this, destination).apply {
        extras?.let { putExtras(it.toBundle()) }
    }
    if (this !is Activity) {
        // If the call is not from an Activity, we must add this flag.
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}

/**
 * Converts a Map<String, Any> into a Bundle safely.
 *
 * Supported Types:
 *  - String
 *  - Int
 *  - Boolean
 *  - Float
 *  - Long
 *  - Double
 *  - CharSequence
 *  - Parcelable
 *  - Serializable
 *
 * If an unsupported type is passed → it will throw IllegalArgumentException.
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
            else -> throw IllegalArgumentException("Unsupported type for key: $key -> ${value::class.java.name}")
        }
    }
    return bundle
}
