package com.zeesofttechlibraries.core.extensions

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.zeesofttechlibraries.core.R

object ShowCustomToast {

    // ✅ SINGLE global toast reference (system + custom)
    private var toast: Toast? = null

    /**
     * Version-safe custom toast with auto-cancellation
     */
    fun Context.showCustomToast(
        message: String,
        @DrawableRes icon: Int = R.drawable.default_toast_icon,
        @ColorRes textColor: Int = R.color.white,
        @ColorRes bgColor: Int = R.color.mainColor,
        @DrawableRes bgDrawable: Int = R.drawable.custom_toast_bg,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        // ✅ Always cancel previous toast (system or custom)
        toast?.cancel()
        toast = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showSystemToast(message, duration)
        } else {
            showLegacyCustomToast(
                message,
                icon,
                textColor,
                bgColor,
                bgDrawable,
                duration
            )
        }
    }

    /**
     * Android 8+ SAFE system toast (auto-cancellable)
     */
    private fun Context.showSystemToast(
        message: String,
        duration: Int
    ) {
        toast = Toast.makeText(
            applicationContext,
            message,
            duration
        ).apply {
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
            show()
        }
    }

    /**
     * Android < 8 full custom toast
     */
    private fun Context.showLegacyCustomToast(
        message: String,
        @DrawableRes icon: Int,
        @ColorRes textColor: Int,
        @ColorRes bgColor: Int,
        @DrawableRes bgDrawable: Int,
        duration: Int
    ) {
        val view = LayoutInflater.from(this)
            .inflate(R.layout.custom_toast, null)

        val card = view.findViewById<LinearLayout>(R.id.card)
        val messageView = view.findViewById<TextView>(R.id.message)
        val iconView = view.findViewById<ImageView>(R.id.icon)

        messageView.text = message
        messageView.setTextColor(ContextCompat.getColor(this, textColor))

        if (icon != R.drawable.default_toast_icon) {
            iconView.visibility = View.VISIBLE
            iconView.setImageResource(icon)
        } else {
            iconView.visibility = View.GONE
        }

        if (bgColor != R.color.mainColor) {
            card.setBackgroundColor(ContextCompat.getColor(this, bgColor))
        } else {
            card.background = ContextCompat.getDrawable(this, bgDrawable)
        }

        toast = Toast(this).apply {
            this.view = view
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
            this.duration = duration
            show()
        }
    }
}
