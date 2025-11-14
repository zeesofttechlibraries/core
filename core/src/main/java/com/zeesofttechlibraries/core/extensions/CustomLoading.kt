package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Shader
import android.os.Build
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
import android.graphics.RenderEffect
import android.view.WindowManager
import android.widget.ImageView
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
    fun Dialog.applyBackgroundBlur(context: Context, radius: Float = 25f) {
        val activityRoot = (context as? Activity)?.window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
            ?: return

        activityRoot.post {
            // 1️⃣ Capture activity content behind dialog
            val bitmap = captureView(activityRoot)

            // 2️⃣ Create overlay
            val overlay = ImageView(context)
            overlay.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            overlay.scaleType = ImageView.ScaleType.FIT_XY
            overlay.alpha = 0.8f // adjust for frosted glass effect

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                overlay.setRenderEffect(
                    RenderEffect.createBlurEffect(
                        30f, 30f, Shader.TileMode.CLAMP
                    )
                )
            }

        }
    }
    fun captureView(view: View): Bitmap {
        // Make sure the view has been measured & laid out
        if (view.width == 0 || view.height == 0) {
            view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        }

        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }







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
        isBlurred:Boolean=false
    ) {

        // Prevent multiple dialogs from stacking on screen
        if (::dialog.isInitialized && dialog.isShowing) return

        // Inflate custom loading layout
        val customView = LayoutInflater.from(this).inflate(R.layout.custom_loading, null)
        val rootMainView = customView.findViewById<LinearLayout>(R.id.main)

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
        dialog = Dialog(this,R.style.TransparentDialog).apply {
            setContentView(customView)
            setCancelable(isCancelable)

            // Transparent background so custom card shape is visible
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            // Fullscreen overlay (center card inside)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            if(isBlurred){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                    window?.setBackgroundBlurRadius(10) // adjust intensity
                }
            }

            show()
//            applyBackgroundBlur(this@showCustomLoading,40f)

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
