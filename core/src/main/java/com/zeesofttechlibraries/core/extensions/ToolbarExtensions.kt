package com.zeesofttechlibraries.core.extensions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.zeesofttechlibraries.core.R

private var customBackAction: (() -> Unit)? = null

/**
 * Sets up a toolbar with:
 * - Dynamic background drawable
 * - Title truncation
 * - Optional custom back button action
 */

/**
 * @param: title: String? = "",
 * @param: iconResId: Int = R.drawable.back_ic,
 * @param: backgroundDrawable: Int? = null,
 * @param: customAction: (() -> Unit)? = null
 */
fun AppCompatActivity.setupToolbar(
    title: String? = "",
    @DrawableRes iconResId: Int = R.drawable.back_ic,
    @DrawableRes backgroundDrawable: Int? = null,
    @DrawableRes menuIcon1: Int? = null,
    @DrawableRes menuIcon2: Int? = null,
    @ColorRes textColor:Int = R.color.white,
    customAction: (() -> Unit)? = null,
    onMenu1Click: (() -> Unit)? = null,
    onMenu2Click: (() -> Unit)? = null
) {
    val btnBack: ImageView? = findViewById(R.id.btnBack)
    val btnMenu1: ImageView? = findViewById(R.id.btn1)
    val btnMenu2: ImageView? = findViewById(R.id.btn2)
    val toolbarTitle: TextView? = findViewById(R.id.toolbarTitle)
    val toolbarLayout: LinearLayout? = findViewById(R.id.toolbarLayout)

    // === Apply dynamic background drawable ===
    backgroundDrawable?.let { toolbarLayout?.setBackgroundResource(it) }

    // === Handle title safely (max 25 chars) ===
    val MAX_CHAR = if(menuIcon1!=null && menuIcon2!=null) 22 else 26
    val safeTitle = title?.let {
        if (it.length > MAX_CHAR) it.take(MAX_CHAR) + "…" else it
    } ?: ""
    toolbarTitle?.text = safeTitle
    toolbarTitle?.setTextColor(ContextCompat.getColor(this, textColor))

    // === Setup back button ===
    btnBack?.setImageResource(iconResId)
    menuIcon1?.let {btnMenu1?.setImageResource(it);btnMenu1?.makeVisible() }
    menuIcon2?.let {btnMenu2?.setImageResource(it);btnMenu2?.makeVisible() }

    val isRTl = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL
    btnBack?.scaleX = if (isRTl) -1f else 1f

    // Set custom or default back action
    if (customAction != null) {
        customBackAction = customAction
    }

    btnBack?.setDebouncedClickListener {
        customBackAction?.invoke() ?: onBackPressedDispatcher.onBackPressed()
    }
    btnMenu1?.setDebouncedClickListener {
        onMenu1Click?.invoke()
    }
    btnMenu2?.setDebouncedClickListener {
        onMenu2Click?.invoke()
    }
}
