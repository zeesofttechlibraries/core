//package com.example.googleadsutils_java.GoogleAds.extensions
//
//import androidx.appcompat.app.AppCompatActivity
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.annotation.DrawableRes
//import com.example.core.R
//
//private var customBackAction: (() -> Unit)? = null
//
///**
// * Sets up a toolbar with:
// * - Dynamic background drawable
// * - Title truncation
// * - Optional custom back button action
// */
//fun AppCompatActivity.setupToolbar(
//    title: String? = "",
//    @DrawableRes iconResId: Int = R.drawable.ic_back,
//    @DrawableRes backgroundDrawable: Int? = null,
//    customAction: (() -> Unit)? = null
//) {
//    val btnBack: ImageView? = findViewById(R.id.btnBack)
//    val toolbarTitle: TextView? = findViewById(R.id.toolbarTitle)
//    val toolbarLayout: LinearLayout? = findViewById(R.id.toolbarLayout)
//
//    // === Apply dynamic background drawable ===
//    backgroundDrawable?.let { toolbarLayout?.setBackgroundResource(it) }
//
//    // === Handle title safely (max 25 chars) ===
//    val MAX_CHAR = 25
//    val safeTitle = title?.let {
//        if (it.length > MAX_CHAR) it.take(MAX_CHAR) + "…" else it
//    } ?: ""
//    toolbarTitle?.text = safeTitle
//
//    // === Setup back button ===
//    btnBack?.setImageResource(iconResId)
//
//    // Flip for RTL (like Arabic)
//    val shouldFlip = resources.configuration.locales[0].language.startsWith("ar", ignoreCase = true)
//    btnBack?.scaleX = if (shouldFlip) -1f else 1f
//
//    // Set custom or default back action
//    if (customAction != null) {
//        customBackAction = customAction
//    }
//
//    btnBack?.setOnClickListener {
//        customBackAction?.invoke() ?: onBackPressedDispatcher.onBackPressed()
//    }
//}
