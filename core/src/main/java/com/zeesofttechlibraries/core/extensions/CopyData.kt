package com.zeesofttechlibraries.core.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * Utility object for copying text to the clipboard.
 * Provides a convenient extension function to copy strings and show a toast.
 */
object CopyData {

    /**
     * Copies the string to the clipboard and shows a short toast message.
     *
     * @param context The context used to access the clipboard service and show the toast.
     */
    fun String.copyToClipboard(context: Context) {
        // Get ClipboardManager safely
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                ?: return  // Exit if ClipboardManager is null

        // Create a ClipData object with the label and string
        val clipData = ClipData.newPlainText("label", this)
        clipboardManager.setPrimaryClip(clipData)

        // Show a toast message
        ToastManager.showToast(context,"Text Copied")
        LoadingDialogManager.showLoadingDialog(context)
    }
}
