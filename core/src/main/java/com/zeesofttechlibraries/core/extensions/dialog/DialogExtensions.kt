package com.spellchecker.core.dialog

import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.zeesofttechlibraries.core.R
import java.lang.ref.WeakReference

// Weak reference prevents leaks across activities
private var activeDialogRef: WeakReference<BaseDialog>? = null

/**
 * Displays a custom dialog with dynamic style, lifecycle safety, and leak prevention.
 * Automatically clears on destroy and prevents multiple dialogs at once.
 */
fun Context.showCustomDialog(
    title: String,
    message: CharSequence,
    negativeText: String = "Cancel",
    positiveText: String = "OK",
    negativeAction: (() -> Unit)? = null,
    positiveAction: (() -> Unit)? = null,
    @StyleRes animStyle: Int? = null,
    hideNegativeButton: Boolean? = null,
    hidePositiveButton: Boolean? = null,
    cancelable: Boolean = false,
    cancelOnTouchOutside: Boolean = cancelable,
    onDismiss: (() -> Unit)? = null,
    @LayoutRes layoutId: Int = R.layout.simple_dialog,
    styleModel: DialogStyleModel? = null
) {
    val activity = this as? Activity ?: return
    if (activity.isFinishing || activity.isDestroyed) return

    // Skip if another dialog is already showing
    if (isDialogActive()) return

    val dialogInstance = createDialogInstance(
        activity = activity,
        layoutId = layoutId,
        animStyle = animStyle,
        cancelable = cancelable,
        cancelOnTouchOutside = cancelOnTouchOutside,
        title = title,
        message = message,
        negativeText = negativeText,
        positiveText = positiveText,
        negativeAction = negativeAction,
        positiveAction = positiveAction,
        hideNegativeButton = hideNegativeButton,
        hidePositiveButton = hidePositiveButton,
        styleModel = styleModel
    )

    attachDismissListener(dialogInstance, onDismiss)
    attachLifecycleObserver(activity)

    dialogInstance.show()
    activeDialogRef = WeakReference(dialogInstance)
}

/** --- Helper Functions Below --- **/

private fun isDialogActive(): Boolean {
    return activeDialogRef?.get()?.isShowing == true
}

/**
 * Creates a fully configured [BaseDialog] instance.
 */
private fun createDialogInstance(
    activity: Activity,
    layoutId: Int,
    animStyle: Int?,
    cancelable: Boolean,
    cancelOnTouchOutside: Boolean,
    title: String,
    message: CharSequence,
    negativeText: String,
    positiveText: String,
    negativeAction: (() -> Unit)?,
    positiveAction: (() -> Unit)?,
    hideNegativeButton: Boolean?,
    hidePositiveButton: Boolean?,
    styleModel: DialogStyleModel?
): BaseDialog {
    return object : BaseDialog(
        activity = activity,
        layoutId = layoutId,
        animStyle = animStyle,
        cancelable = cancelable,
        canceledOnTouchOutside = cancelOnTouchOutside
    ) {
        override fun onContentInflated(contentView: View) {
            val titleView = contentView.findViewById<TextView>(R.id.text_title)
            val messageView = contentView.findViewById<TextView>(R.id.text_message)
            val iconView = contentView.findViewById<ImageView>(R.id.image_icon)
            val rootLayout = contentView.findViewById<LinearLayout>(R.id.root_linear_layout)
            val negativeBtn = contentView.findViewById<TextView>(R.id.button_negative)
            val positiveBtn = contentView.findViewById<TextView>(R.id.button_positive)

            setupText(titleView, messageView, title, message)
            applyStyle(styleModel, rootLayout, iconView, positiveBtn, negativeBtn)
            setupButtons(
                dialog = this,
                negativeBtn = negativeBtn,
                positiveBtn = positiveBtn,
                negativeText = negativeText,
                positiveText = positiveText,
                negativeAction = negativeAction,
                positiveAction = positiveAction,
                hideNegativeButton = hideNegativeButton,
                hidePositiveButton = hidePositiveButton
            )
        }
    }
}

/**
 * Binds title and message text to dialog.
 */
private fun setupText(titleView: TextView, messageView: TextView, title: String, message: CharSequence) {
    titleView.text = title
    messageView.text = message
}

/**
 * Applies the dialog’s visual styling.
 */
private fun applyStyle(
    model: DialogStyleModel?,
    rootLayout: LinearLayout,
    iconView: ImageView,
    positiveBtn: TextView,
    negativeBtn: TextView
) {
    model ?: return

    model.backgroundDrawable?.let { rootLayout.setBackgroundResource(it) }
    model.iconDrawable?.let {
        iconView.setImageResource(it)
        iconView.visibility = View.VISIBLE
    }
    model.iconTintColor?.let { iconView.setColorFilter(it, PorterDuff.Mode.SRC_IN) }
    model.positiveButtonBackground?.let { positiveBtn.setBackgroundResource(it) }
    model.negativeButtonBackground?.let { negativeBtn.setBackgroundResource(it) }
}

/**
 * Configures button labels, visibility, and click listeners.
 */
private fun setupButtons(
    dialog: BaseDialog,
    negativeBtn: TextView,
    positiveBtn: TextView,
    negativeText: String,
    positiveText: String,
    negativeAction: (() -> Unit)?,
    positiveAction: (() -> Unit)?,
    hideNegativeButton: Boolean?,
    hidePositiveButton: Boolean?
) {
    // Negative Button
    if (hideNegativeButton == true) {
        negativeBtn.visibility = View.GONE
    } else {
        negativeBtn.apply {
            text = negativeText
            visibility = View.VISIBLE
            setOnClickListener {
                negativeAction?.invoke()
                dialog.dismiss()
            }
        }
    }

    // Positive Button
    if (hidePositiveButton == true) {
        positiveBtn.visibility = View.GONE
    } else {
        positiveBtn.apply {
            text = positiveText
            visibility = View.VISIBLE
            setOnClickListener {
                positiveAction?.invoke()
                dialog.dismiss()
            }
        }
    }
}

/**
 * Sets dismiss listener to clear memory references and invoke optional callback.
 */
private fun attachDismissListener(dialogInstance: BaseDialog, onDismiss: (() -> Unit)?) {
    dialogInstance.setOnDialogDismissed {
        activeDialogRef = null
        onDismiss?.invoke()
    }
}

/**
 * Observes the activity lifecycle and dismisses dialog on destroy.
 */
private fun attachLifecycleObserver(activity: Activity) {
    if (activity is LifecycleOwner) {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                activeDialogRef?.get()?.dismiss()
                activeDialogRef = null
                activity.lifecycle.removeObserver(this)
            }
        })
    }
}
