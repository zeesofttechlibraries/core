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
import java.lang.ref.WeakReference

/**
 * CustomLoading:
 * Utility for showing a full-screen loading dialog with optional blur effect on the underlying Activity.
 * Supports Lottie animation or ProgressBar, custom colors, text, and square layouts.
 */
object CustomLoading {

    // Single instance of the Dialog to prevent multiple dialogs from showing at the same time
    private lateinit var dialog: Dialog

    // Optional ImageView overlay for blur effect
    private var blurOverlay: WeakReference<ImageView>? = null

    /**
     * showCustomLoading()
     *
     * Displays a customizable loading dialog with optional blur.
     *
     * @param lifeCycleOwner   LifecycleOwner to automatically dismiss dialog when Activity/Fragment is destroyed.
     * @param loadingMessage   Text to display under the animation or progress bar.
     * @param lottieRaw        Optional Lottie animation resource (default splash_loading).
     * @param bgColor          Optional background color for the card.
     * @param textColor        Color for the loading text.
     * @param bgDrawable       Optional drawable background for the card.
     * @param isSquarer        If true, makes the card square-ish and centers content.
     * @param isCancelable     Whether tapping outside dismisses the dialog.
     * @param isBlurred        Whether to blur the underlying activity.
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

        // Get the Activity's root view for adding blur overlay
        // android.R.id.content contains all your Activity UI
        val activityRoot = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)

        // Create a transparent ImageView that will hold the blurred screenshot
        val overly = ImageView(this).apply {
            // Transparent so we see only the blurred bitmap
            setBackgroundColor(ContextCompat.getColor(this@showCustomLoading,android.R.color.transparent))
            alpha = 0f // start invisible, will fade in
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP // ensures the bitmap scales properly
        }

        // Add the overlay to the root view, above all existing content
        activityRoot.addView(overly)
        blurOverlay = WeakReference(overly)


        // Inflate your custom loading dialog layout
        val customView = LayoutInflater.from(this).inflate(R.layout.custom_loading, null)

        // Wrap it in a FrameLayout for optional padding/margin
        val wrappedLayout = FrameLayout(this).apply {
            val margin = (10 * activity.resources.displayMetrics.density).toInt()
            setPadding(margin, 0, margin, 0)
            addView(customView) // add the dialog layout inside the wrapper
        }

        /**
         * Run code inside post{} to ensure the root layout is fully laid out.
         * This guarantees width/height are measured so Blurry can capture a valid bitmap.
         */
        activityRoot.post {

            // If blur is requested, apply it to the root view
            if (isBlurred) {
                blurOverlay?.get().let {
                    addBlur(activityRoot, activity, it)
                }
            }

            // Prevent creating multiple dialogs if one is already showing
            if (::dialog.isInitialized && dialog.isShowing) return@post

            // Get references to key views inside the dialog layout
            val viewText = wrappedLayout.findViewById<TextView>(R.id.loadingText)
            val lottie = wrappedLayout.findViewById<LottieAnimationView>(R.id.lottie)
            val progressBar = wrappedLayout.findViewById<ProgressBar>(R.id.progressBar2)
            val card = wrappedLayout.findViewById<LinearLayout>(R.id.card)
            val mainCard = wrappedLayout.findViewById<CardView>(R.id.mainCard)

            // Set the loading text and color
            viewText.text = loadingMessage
            viewText.setTextColor(ContextCompat.getColor(this, textColor))

            // Apply custom drawable background if provided
            if (bgDrawable != R.drawable.custom_toast_bg) {
                card.background = ContextCompat.getDrawable(this, bgDrawable)
            }

            // Apply custom background color if provided
            if (bgColor != R.color.mainColor) {
                card.setBackgroundColor(ContextCompat.getColor(this, bgColor))
            }

            // Show Lottie animation if provided; otherwise show ProgressBar
            if (lottieRaw != R.raw.splash_loading) {
                lottie.visibility = LottieAnimationView.VISIBLE
                progressBar.visibility = ProgressBar.GONE
                lottie.setAnimation(lottieRaw)
            } else {
                lottie.visibility = LottieAnimationView.GONE
                progressBar.visibility = ProgressBar.VISIBLE
            }

            // Adjust layout if a square card is requested
            if (isSquarer) {
                card.orientation = LinearLayout.VERTICAL
                card.gravity = Gravity.CENTER or Gravity.CENTER_HORIZONTAL
                viewText.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

                val params = mainCard.layoutParams
                params.width = 500 // fixed square width
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                mainCard.layoutParams = params
            }

            // Create and show the dialog
            dialog = Dialog(this).apply {
                setContentView(wrappedLayout) // important: pass wrappedLayout, not customView
                setCancelable(isCancelable)

                // Make window transparent so we can see the blur behind the card
                window?.setBackgroundDrawableResource(android.R.color.transparent)

                // Set fullscreen size
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                // Dismiss listener removes the blur overlay safely
                setOnDismissListener {
                    blurOverlay?.get().let {
                        removeBlur(
                            activityRoot,
                            it,
                            setBlurOverlayNull = { blurOverlay = null }
                        )
                    }
                }

                show()
            }

            // Add lifecycle observer to automatically dismiss the dialog when Activity/Fragment is destroyed
            lifeCycleOwner.lifecycle.addObserver(
                LoadingDialogLifecycleObserver(dialog)
            )
        }
    }

    /**
     * dismissDialog():
     * Safely dismiss the dialog and remove blur from activity root.
     * @param context Activity context (required for removing blur).
     */
    fun dismissDialog(context: Context) {
        if (::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()

            // Remove any blurred bitmap from the activity root
            val activityRoot =
                (context as Activity).window.decorView.findViewById<ViewGroup>(android.R.id.content)
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
