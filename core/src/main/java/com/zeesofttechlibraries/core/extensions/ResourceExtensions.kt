package com.zeesofttechlibraries.core.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**
 * Utility extensions for easy access to app resources
 * such as colors, drawables, and strings.
 */

/**
 * Safely fetch a color resource.
 *
 * Usage:
 *  val color = context.getColorResource(R.color.primaryColor)
 */
fun Context.getColorResource(@ColorRes resId: Int): Int {
    return ContextCompat.getColor(this, resId)
}

/**
 * Safely fetch a drawable resource.
 *
 * Usage:
 *  val icon = context.getDrawableResource(R.drawable.ic_logo)
 */
fun Context.getDrawableResource(@DrawableRes resId: Int): Drawable? {
    return ContextCompat.getDrawable(this, resId)
}

/**
 * Safely fetch a string resource.
 *
 * Usage:
 *  val welcomeText = context.getStringResource(R.string.welcome)
 */
fun Context.getStringResource(@StringRes resId: Int): String {
    return this.getString(resId)
}
