package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.airbnb.lottie.LottieAnimationView
import com.zeesofttechlibraries.core.R
import com.zeesofttechlibraries.core.extensions.BaseBlurry.addBlur
import com.zeesofttechlibraries.core.extensions.BaseBlurry.removeBlur
import jp.wasabeef.blurry.Blurry

/**
 * CustomLoading:
 * Utility for showing a full-screen loading dialog with optional blur effect on the underlying Activity.
 * Supports Lottie animation or ProgressBar, custom colors, text, and square layouts.
 */
object CustomLoading {

    // Single instance of the Dialog to prevent multiple dialogs showing simultaneously.
    private lateinit var dialog: Dialog
    private var blurOverlay: ImageView ? = null

    /**
     * showCustomLoading()
     *
     * Displays a customizable loading dialog.
     *
     * @param lifeCycleOwner   Lifecycle owner to automatically dismiss dialog when destroyed.
     * @param loadingMessage   Text displayed below the animation or progress bar.
     * @param lottieRaw        Optional Lottie animation resource (default splash_loading).
     * @param bgColor          Optional background color for the card.
     * @param textColor        Color for loading text.
     * @param bgDrawable       Optional drawable background for card.
     * @param isSquarer        If true, makes the card square-ish and centered.
     * @param isCancelable     Allows closing by tapping outside the dialog.
     * @param isBlurred        If true, blurs the underlying activity.
     */
    fun Context.showCustomLoading(
        lifeCycleOwner: LifecycleOwner,
        loadingMessage: String,
        @RawRes lottieRaw: Int = R.raw.splash_loading,
        @ColorRes bgColor: Int = R.color.mainColor,
        @ColorRes textColor: Int = R.color.white,
        @DrawableRes bgDrawable: Int = R.drawable.custom_toast_bg,
        isSquarer: Boolean = false,
        isCancelable: Boolean = false,
        isBlurred: Boolean = false,
    ) {
        val activity = this as Activity

        // Get root view of the Activity. This is the layout that will be blurred.
        val activityRoot = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)

        blurOverlay = ImageView(this).apply {
            setBackgroundColor(android.R.color.transparent)
            alpha = 0f
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        activityRoot.addView(blurOverlay)
        // Inflate custom dialog layout
        val customView = LayoutInflater.from(this).inflate(R.layout.custom_loading, null)

        val wrappedLayout = FrameLayout(this).apply {
            val margin = (10*activity.resources.displayMetrics.density).toInt()
            setPadding(margin,0,margin,0)
            addView(customView)
        }
        // Optional ImageView in layout to hold blurred background

        // Ensure the root layout is fully laid out before trying to capture its bitmap for blur
            activityRoot.post {

            // Apply blur only if requested
            if (isBlurred) {
                addBlur(activityRoot,activity,blurOverlay)
            }

            // Prevent multiple dialogs showing simultaneously
            if (::dialog.isInitialized && dialog.isShowing) return@post

            // Get references to dialog views
            val viewText = wrappedLayout.findViewById<TextView>(R.id.loadingText)
            val lottie = wrappedLayout.findViewById<LottieAnimationView>(R.id.lottie)
            val progressBar = wrappedLayout.findViewById<ProgressBar>(R.id.progressBar2)
            val card = wrappedLayout.findViewById<LinearLayout>(R.id.card)
            val mainCard = wrappedLayout.findViewById<CardView>(R.id.mainCard)

            // Set the loading text and color
            viewText.text = loadingMessage
            viewText.setTextColor(ContextCompat.getColor(this, textColor))

            // Set custom background drawable if provided
            if (bgDrawable != R.drawable.custom_toast_bg) {
                card.background = ContextCompat.getDrawable(this, bgDrawable)
            }

            // Set custom background color if provided
            if (bgColor != R.color.mainColor) {
                card.setBackgroundColor(ContextCompat.getColor(this, bgColor))
            }

            // Show either Lottie animation or ProgressBar
            if (lottieRaw != R.raw.splash_loading) {
                lottie.visibility = LottieAnimationView.VISIBLE
                progressBar.visibility = ProgressBar.GONE
                lottie.setAnimation(lottieRaw)
            } else {
                lottie.visibility = LottieAnimationView.GONE
                progressBar.visibility = ProgressBar.VISIBLE
            }

            // Adjust layout for square card if requested
            if (isSquarer) {
                card.orientation = LinearLayout.VERTICAL
                card.gravity = Gravity.CENTER or Gravity.CENTER_HORIZONTAL
                viewText.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

                val params = mainCard.layoutParams
                params.width = 500 // square width
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                mainCard.layoutParams = params
            }

            // Create and show the dialog
            dialog = Dialog(this).apply {
                setContentView(wrappedLayout)
                setCancelable(isCancelable)

                // Transparent window so custom card and blur are visible
                window?.setBackgroundDrawableResource(android.R.color.transparent)

                // Fullscreen layout
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setOnDismissListener {
                    removeBlur(
                        activityRoot,  blurOverlay,
                        setBlurOverlayNull = {blurOverlay = null}
                    )
                }
                show()

            }
                lifeCycleOwner.lifecycle.addObserver(
            LoadingDialogLifecycleObserver(dialog)
        )
            // Make dialog lifecycle-aware to prevent leaks

        }
    }

    /**
     * dismissDialog():
     * Safely dismiss the dialog and remove blur from activity root.
     *
     * @param context The context from which the dialog was created (Activity required for blur cleanup).
     */
    fun dismissDialog(context: Context) {
        if (::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()

            // Remove blurred bitmap from the activity
            val activityRoot = (context as Activity).window.decorView.findViewById<ViewGroup>(android.R.id.content)
            Blurry.delete(activityRoot)
        }
    }

    /**
     * isDialogShowing():
     * Returns true if the loading dialog is currently visible.
     */
    fun isDialogShowing(): Boolean {
        return ::dialog.isInitialized && dialog.isShowing
    }
}
