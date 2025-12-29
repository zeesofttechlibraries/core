package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.annotation.RawRes
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.airbnb.lottie.LottieAnimationView
import com.zeesofttechlibraries.core.R
import java.lang.ref.WeakReference

object LoadingDialogManager {

    private var loadingDialogRef: WeakReference<Dialog>? = null

    /**
     * Shows a non-cancelable loading dialog.
     *
     * ✅ Safe to call multiple times — it won't show duplicates.
     * ✅ Automatically dismisses when Activity is destroyed (prevents window leaks).
     * ✅ Works only when context is an Activity.
     *
     * @param context The context to use for showing the dialog.
     *
     * Usage:
     *     LoadingDialogManager.showLoadingDialog(this)
     *
     * Usage from Fragment:
     *     LoadingDialogManager.showLoadingDialog(requireContext())
     *
     * Make sure your `loading.xml` layout exists in `res/layout/`.
     */
    @JvmStatic
    fun showLoadingDialog(context: Context,@RawRes lottieAnimation: Int?=null) {
        val activity = context as? Activity ?: return
        if (activity.isFinishing || activity.isDestroyed) return

        val currentDialog = loadingDialogRef?.get()
        if (currentDialog?.isShowing == true) return

        dismissLoadingDialog(context)

        val view = LayoutInflater.from(context).inflate(R.layout.loading, null)
        val lottieAnim = view.findViewById<LottieAnimationView>(R.id.lottieAnim)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        if(lottieAnimation!=null){
            lottieAnim.makeVisible()
            progressBar.makeGone()
            lottieAnim.setAnimation(lottieAnimation)
        }
        val dialog = Dialog(activity, R.style.TransparentDialog).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(view)
            window?.apply {
                setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
                addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                attributes = attributes.apply { dimAmount = 0.5f } // background dim
                setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setGravity(Gravity.CENTER)
            }
            setOnDismissListener { loadingDialogRef = null }
            show()
        }

        // Automatically removes dialog when lifecycle ends
        if (activity is LifecycleOwner) {
            activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    if (dialog.isShowing) dialog.dismiss()
                    loadingDialogRef = null
                }
            })
        }

        loadingDialogRef = WeakReference(dialog)
    }

    /**
     * Dismiss the currently displayed loading dialog.
     *
     * Safe to call even if:
     * - No dialog is showing
     * - Activity is already finishing/destroyed
     *
     * @param context The context to use.
     *
     * Usage:
     *     LoadingDialogManager.dismissLoadingDialog(this)
     *
     * From Fragment:
     *     LoadingDialogManager.dismissLoadingDialog(requireContext())
     */
    @JvmStatic
    fun dismissLoadingDialog(context: Context) {
        val dialog = loadingDialogRef?.get()
        try {
            val activity = context as? Activity
            if (dialog?.isShowing == true) {
                if (activity == null || (!activity.isFinishing && !activity.isDestroyed)) {
                    dialog.dismiss()
                }
            }
        } catch (_: Exception) {
            // Ignoring window token exception when activity is gone
        } finally {
            loadingDialogRef = null
        }
    }
}
