package com.spellchecker.core.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes

/**
 * An open, abstract [Dialog] class that simplifies boilerplate for custom dialogs.
 *
 * It manages window features, lifecycle, and content inflation, allowing subclasses
 * to focus on implementing their specific logic in [onContentInflated].
 *
 * Key Features:
 * - **Lifecycle Aware**: Automatically handles `super.onStop()` to avoid crashes on rotation.
 * - **Custom Layout**: Inflates a specified layout resource for the dialog's content.
 * - **Optional Animations**: Supports custom window animations via a style resource.
 * - **Dismiss Callback**: Provides a listener for when the dialog is dismissed.
 *
 * Example usage:
 * ```
 * class MyCustomDialog(activity: Activity) : BaseDialog(activity, R.layout.my_dialog_layout) {
 *     override fun onContentInflated(contentView: View) {
 *         val okButton = contentView.findViewById<Button>(R.id.button_ok)
 *         okButton.setOnClickListener {
 *             // Handle action
 *             dismiss()
 *         }
 *     }
 * }
 * ```
 */
abstract class BaseDialog(
    activity: Activity,
    @LayoutRes private val layoutId: Int,
    @StyleRes private val animStyle: Int? = null,
    cancelable: Boolean = true,
    canceledOnTouchOutside: Boolean = true
) : Dialog(activity) {

    private var onDismissListener: (() -> Unit)? = null

    init {
        setCancelable(cancelable)
        setCanceledOnTouchOutside(canceledOnTouchOutside)
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Basic window setup
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        animStyle?.let { window?.attributes?.windowAnimations = it }

        // Inflate custom layout
        val contentView = LayoutInflater.from(context).inflate(layoutId, null)
        setContentView(contentView)

        // Let subclasses configure the inflated view
        onContentInflated(contentView)
    }

    /**
     * Called after the custom layout has been inflated.
     *
     * This is the primary method where subclasses should initialize their views,
     * set up listeners, and bind data.
     *
     * @param contentView The root view of the inflated layout.
     */
    abstract fun onContentInflated(contentView: View)

    /**
     * Sets a callback to be invoked when the dialog is dismissed.
     */
    fun setOnDialogDismissed(listener: () -> Unit) {
        this.onDismissListener = listener
    }

    override fun onStop() {
        // This override is intentionally left empty. Calling super.onStop() can cause
        // a crash if the dialog is dismissed and the activity is rotated simultaneously.
        // The default implementation tries to save the dialog's state, but since the view
        // is already detached, it fails. By not calling super, we avoid this issue.
    }

    override fun dismiss() {
        super.dismiss()
        onDismissListener?.invoke()
    }
}
