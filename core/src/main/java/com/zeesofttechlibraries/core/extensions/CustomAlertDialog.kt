package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
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
import java.lang.ref.WeakReference

/**
 * CustomAlertDialog
 *
 * A reusable, lifecycle-aware alert dialog builder designed for Android projects.
 * Supports:
 *  - Custom icons
 *  - Lottie animations
 *  - Dynamic button backgrounds & colors
 *  - Optional background blur effect
 *  - Safe lifecycle dismissal (prevents memory leaks)
 *
 * This is implemented as an object (singleton) to simplify usage
 * and ensure only one dialog is visible at a time.
 */
object CustomAlertDialog {

    // Active dialog instance
    private lateinit var dialog: Dialog

    // Overlay image used to draw blur effect behind dialog
    private var blurOverly: WeakReference<ImageView>? = null


    /**
     * Shows a fully customizable alert dialog.
     *
     * @param lifeCycleOwner  Lifecycle owner to safely auto-dismiss on destroy
     * @param title           Title text
     * @param description     Description text
     * @param topBgColor      Optional top header background color
     * @param icon            Optional static icon
     * @param lottieAnimation Optional Lottie animation instead of icon
     * @param positiveButtonBg Background drawable of primary button
     * @param negativeButtonBg Background drawable of secondary button
     * @param positiveButtonTextColor Color of primary button text
     * @param negativeButtonTextColor Color of secondary button text
     * @param positiveButtonText Text for primary action button
     * @param negativeButtonText Optional text for secondary button
     * @param isBlurred       If true → blur background when dialog opens
     * @param isCancelable    If true → dialog is cancelable via back press
     * @param isOnTouchOutsideCancel If true → clicking outside closes dialog
     * @param positiveButtonAction Lambda executed on primary button click
     * @param negativeButtonAction Lambda executed on secondary button click
     */
    fun Context.showCustomAlertDialog(
        lifeCycleOwner: LifecycleOwner,
        title: String,
        description: String,
        @ColorRes topBgColor: Int? = null,
        @DrawableRes icon: Int? = null,
        @RawRes lottieAnimation: Int? = null,
        @DrawableRes positiveButtonBg: Int = R.drawable.btn_bg,
        @DrawableRes negativeButtonBg: Int = R.drawable.btn_red_bg,
        @ColorRes positiveButtonTextColor: Int = R.color.alertDialogDefaultColor,
        @ColorRes negativeButtonTextColor: Int = R.color.alertRed,
        positiveButtonText: String = "ok",
        negativeButtonText: String? = null,
        isBlurred: Boolean = false,
        isCancelable: Boolean = false,
        isOnTouchOutsideCancel: Boolean = false,
        positiveButtonAction: () -> Unit,
        negativeButtonAction: (() -> Unit)?=null
    ) {

        val activity = (this as Activity)

        // Root content view used for blur overlay
        val rootView = activity.window.decorView.rootView.findViewById<ViewGroup>(android.R.id.content)

        // Transparent overlay where the blurred bitmap is drawn
        val overly = ImageView(activity).apply {
            setBackgroundColor(android.R.color.transparent)
            alpha = 0f
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        rootView.addView(overly)
        blurOverly = WeakReference(overly)


        // Inflate dialog layout
        val alertDialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog, null)

        // Wrap in FrameLayout for applying margin programmatically
        val wrappedLayout = FrameLayout(this).apply {
            val margins = (10 * activity.resources.displayMetrics.density).toInt()
            setPadding(margins, 0, margins, 0)
            addView(alertDialogView)
        }

        rootView.post {

            if (isBlurred) {
                blurOverly?.get()?.let{
                addBlur(rootView, activity, it)
                }
            }

            // Prevent duplicate dialogs
            if (::dialog.isInitialized && dialog.isShowing) return@post

            // View references
            val titleView = wrappedLayout.findViewById<TextView>(R.id.alertTitle)
            val descriptionView = wrappedLayout.findViewById<TextView>(R.id.alertDescription)
            val iconView = wrappedLayout.findViewById<ImageView>(R.id.alertIcon)
            val lottieView = wrappedLayout.findViewById<LottieAnimationView>(R.id.alertLottie)
            val positiveButton = wrappedLayout.findViewById<TextView>(R.id.primaryBtn)
            val negativeButton = wrappedLayout.findViewById<TextView>(R.id.secondaryBtn)
            val topBgColorView = wrappedLayout.findViewById<View>(R.id.topBgColor)
            val card = wrappedLayout.findViewById<CardView>(R.id.card1)

            // Apply main text
            titleView.text = title
            descriptionView.text = description

            // Icon handling
            if (icon != null) {
                lottieView.makeGone()
                iconView.makeVisible()
                iconView.setImageResource(icon)
            }

            // Top header color
            if (topBgColor != null) {
                topBgColorView.setBackgroundColor(ContextCompat.getColor(this@showCustomAlertDialog, topBgColor))
            }

            // Lottie animation handling
            if (lottieAnimation != null) {
                lottieView.makeVisible()
                iconView.makeGone()
                lottieView.setAnimation(lottieAnimation)
            }

            // Primary Button Styling
            positiveButton.text = positiveButtonText
            positiveButton.setBackgroundResource(positiveButtonBg)
            positiveButton.setTextColor(ContextCompat.getColor(this@showCustomAlertDialog, positiveButtonTextColor))

            // Secondary Button Styling
            if (negativeButtonText != null) {
                negativeButton.makeVisible()
                negativeButton.text = negativeButtonText
                negativeButton.setBackgroundResource(negativeButtonBg)
                if( icon == null) iconView.setImageResource(R.drawable.cross)
                negativeButton.setTextColor(ContextCompat.getColor(this@showCustomAlertDialog, negativeButtonTextColor))
            } else {
                negativeButton.makeGone()
            }

            // Primary Button Action
            positiveButton.setDebouncedClickListener {
                positiveButtonAction.invoke()
                dialog.dismiss()
            }

            // Secondary Button Action
            negativeButton.setDebouncedClickListener {
                negativeButtonAction?.invoke()
                dialog.dismiss()
            }

            // Build dialog
            dialog = Dialog(this).apply {
                setContentView(wrappedLayout)
                setCancelable(isCancelable)
                setCanceledOnTouchOutside(isOnTouchOutsideCancel)

                // Transparent background for custom card UI
                window?.apply {
                    setBackgroundDrawableResource(android.R.color.transparent)
                    setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                setOnDismissListener {
                    blurOverly?.get().let {
                        removeBlur(rootView, it) { blurOverly = null }
                    }
                    dismissAlertDialog()
                }

                show()

                // Attach lifecycle observer to avoid memory leaks
                if (::dialog.isInitialized) {
                    lifeCycleOwner.lifecycle.addObserver(
                        LoadingDialogLifecycleObserver(dialog = dialog)
                    )
                }
            }
        }
    }

    /**
     * Programmatically dismiss active dialog.
     * Safe to call even if not showing.
     */
    fun dismissAlertDialog() {
        dialog.dismiss()
    }

    /**
     * Returns true if dialog is currently displayed.
     */
    fun isAlertDialogShowing(): Boolean = dialog.isShowing
}
