package com.zeesofttechlibraries.core.extensions

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.zeesofttechlibraries.core.R

/**
 * ------------------------------------------------------------
 *  ShowCustomToast.kt
 *  Part of ZeesoftTech Core Android Library
 * ------------------------------------------------------------
 *
 *  A lightweight Kotlin utility object that displays a custom Toast
 *  message anywhere within the app.
 *
 *  Features:
 *   • Reusable across multiple modules via dependency import.
 *   • Fully customizable text color, background color/drawable, and icon.
 *   • Automatically cancels any previously visible toast (prevents stacking).
 *   • Works with both Activity and Application context.
 *
 *  Usage Example:
 *  ------------------------------------------------------------
 *  import com.zeesofttechlibraries.core.extensions.ShowCustomToast.showCustomToast
 *
 *  // Simple toast
 *  showCustomToast("Hello from ZeesoftTech!")
 *
 *  // With customization
 *  showCustomToast(
 *      message = "Operation successful",
 *      icon = R.drawable.ic_success,
 *      textColor = R.color.white,
 *      bgColor = R.color.green,
 *      duration = Toast.LENGTH_LONG
 *  )
 *  ------------------------------------------------------------
 *
 *  Layout requirements:
 *  Must include a layout file named `custom_toast.xml` in your
 *  core module under `res/layout/` with these required IDs:
 *      - LinearLayout  →  @+id/card
 *      - ImageView     →  @+id/icon
 *      - TextView      →  @+id/message
 *
 *  Example layout (res/layout/custom_toast.xml):
 *  ------------------------------------------------------------
 *  <LinearLayout
 *      xmlns:android="http://schemas.android.com/apk/res/android"
 *      android:id="@+id/card"
 *      android:orientation="horizontal"
 *      android:padding="12dp"
 *      android:gravity="center_vertical"
 *      android:background="@drawable/custom_toast_bg"
 *      android:layout_width="wrap_content"
 *      android:layout_height="wrap_content">
 *
 *      <ImageView
 *          android:id="@+id/icon"
 *          android:layout_width="24dp"
 *          android:layout_height="24dp"
 *          android:layout_marginEnd="8dp"
 *          android:visibility="gone"
 *          android:contentDescription="@string/app_name" />
 *
 *      <TextView
 *          android:id="@+id/message"
 *          android:layout_width="wrap_content"
 *          android:layout_height="wrap_content"
 *          android:textColor="@color/white"
 *          android:textSize="16sp" />
 *  </LinearLayout>
 *  ------------------------------------------------------------
 */
object ShowModernToast {

    // Reference to the current visible toast (to cancel if a new one appears)
    private var toast: Toast? = null

    /**
     * Extension function on [Context] to show a custom-styled toast message.
     *
     * @param message      The message text to display.
     * @param icon         Drawable resource for icon (optional).
     * @param textColor    Text color resource.
     * @param bgColor      Background color (if not using drawable).
     * @param bgDrawable   Background drawable (default: rounded rectangle).
     * @param duration     Toast duration (Toast.LENGTH_SHORT / Toast.LENGTH_LONG).
     */
    fun Context.showModernToast(
        message: String,
        @DrawableRes icon: Int ?=null,
        @ColorRes textColor: Int?=null,
        @ColorRes bgColor: Int ?= null,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        // Cancel any currently visible toast to prevent overlap
        toast?.cancel()

        // Inflate the custom layout for toast
        val view = LayoutInflater.from(this).inflate(R.layout.modern_custom_toast, null)
        val messageView = view.findViewById<TextView>(R.id.message)
        val iconAlert = view.findViewById<ImageView>(R.id.alertIcon)
        val cardBg = view.findViewById<View>(R.id.cardBg)

        messageView.text = message
        if(icon!=null){
            iconAlert.setImageResource(icon)
        }
       if(textColor!=null){
           messageView.setTextColor(ContextCompat.getColor(this, textColor))
       }
//        if(bgColor!=null){
//            cardBg.setBackgroundColor(ContextCompat.getColor(this, bgColor))
//        }
        if (bgColor != null) {
            val drawable = cardBg.background.mutate() // get current drawable
            drawable.setTint(ContextCompat.getColor(this, bgColor))
            cardBg.background = drawable
        }


        toast = Toast(this).apply {
            view?.let { this.view = it }
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
            this.duration = duration
            show()
        }
    }
}
