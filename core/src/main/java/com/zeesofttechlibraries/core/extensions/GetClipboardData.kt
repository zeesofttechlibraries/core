package com.zeesofttechlibraries.core.extensions

import android.content.ClipboardManager
import android.content.Context

/**
 * Extension function to safely retrieve text from the clipboard.
 *
 * Usage:
 *  val copiedText = context.getClipboardText()
 *
 * @return The text from the clipboard, or an empty string if clipboard is empty or unavailable.
 */
fun Context.getClipboardText(): String {
    // Get ClipboardManager safely
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        ?: return ""  // Return empty string if clipboard service is unavailable

    // Check if clipboard has data
    if (!clipboard.hasPrimaryClip()) return ""

    val clipData = clipboard.primaryClip
    val item = clipData?.getItemAt(0)

    return item?.text?.toString() ?: ""
}
