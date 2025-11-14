package com.zeesofttechlibraries.core.extensions

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

/**
 * CustomLoading:
 * A utility object that provides a global customizable loading dialog.
 * Can display either a progress bar or a Lottie animation with custom text,
 * colors, background, and shape modifications.
 */
object CustomLoading {

    // Lateinit variable to store and maintain a single Dialog instance.
    private lateinit var dialog: Dialog


    /**
     * showCustomLoading()
     *
     * Displays a fully customizable loading dialog.
     *
     * @param lifeCycleOwner   Lifecycle owner to manage the dialog's lifecycle.
     * @param loadingMessage   Text shown below animation or progress bar.
     * @param lottieRaw        Raw resource for Lottie animation (optional).
     * @param bgColor          Background color for loading card (optional).
     * @param textColor        Color for loading text.
     * @param bgDrawable       Drawable resource for background shape.
     * @param isSquarer        If true, dialog becomes vertically-stacked, centered square layout.
     * @param isCancelable     Allows dialog to close when user taps outside.
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
    ) {

        // Prevent multiple dialogs from stacking on screen
        if (::dialog.isInitialized && dialog.isShowing) return

        // Inflate custom loading layout
        val customView = LayoutInflater.from(this).inflate(R.layout.custom_loading, null)

        // Fetch view components
        val viewText = customView.findViewById<TextView>(R.id.loadingText)
        val lottie = customView.findViewById<LottieAnimationView>(R.id.lottie)
        val progressBar = customView.findViewById<ProgressBar>(R.id.progressBar2)
        val card = customView.findViewById<LinearLayout>(R.id.card)
        val mainCard = customView.findViewById<CardView>(R.id.mainCard)

        // Assign loading message text
        viewText.text = loadingMessage
        viewText.setTextColor(ContextCompat.getColor(this, textColor))

        // Apply custom drawable background if user passes their own
        if (bgDrawable != R.drawable.custom_toast_bg) {
            card.background = ContextCompat.getDrawable(this, bgDrawable)
        }

        // Apply custom background color (only if changed from default)
        if (bgColor != R.color.mainColor) {
            card.setBackgroundColor(ContextCompat.getColor(this, bgColor))
        }

        // Show Lottie animation if a custom animation is passed
        if (lottieRaw != R.raw.splash_loading) {
            lottie.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            lottie.setAnimation(lottieRaw)
        } else {
            // Otherwise show default ProgressBar
            lottie.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }

        /**
         * isSquarer == true
         * Modifies the layout to make the card centered, vertical, and square-ish.
         * Useful for modern minimal UI styles.
         */
        if (isSquarer) {
            card.orientation = LinearLayout.VERTICAL
            card.gravity = Gravity.CENTER

            // Modify main card size dynamically
            val params = mainCard.layoutParams
            params.width = 500 // Fixed width to create a square feel
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            mainCard.layoutParams = params
        }

        /**
         * Create and display dialog
         */
        dialog = Dialog(this).apply {
            setContentView(customView)
            setCancelable(isCancelable)

            // Transparent background so custom card shape is visible
            window?.setBackgroundDrawableResource(android.R.color.transparent)

            // Fullscreen overlay (center card inside)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            show()
        }


        /**
         * Make a Dialog lifecycle aware for activity or Fragment.
         * */
        lifeCycleOwner.lifecycle.addObserver(
            LoadingDialogLifecycleObserver(dialog)
        )
    }

    /**
     * dismissDialog():
     * Safely dismiss the dialog if it is showing.
     */
    fun dismissDialog() {
        dialog.dismiss()
    }

    /**
     * isDialogShowing():
     * @return Boolean - True if loading dialog is currently visible.
     */
    fun isDialogShowing(): Boolean {
        return dialog.isShowing
    }
}
