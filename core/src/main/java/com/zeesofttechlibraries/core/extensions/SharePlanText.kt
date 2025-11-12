package com.zeesofttechlibraries.core.extensions

import android.content.Context
import android.content.Intent
import kotlin.apply

/**
 * Extension function to easily share text from any context.
 *
 * @param text The text content to be shared.
 */
fun Context.shareText(text: String) {
    // Create an ACTION_SEND intent
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }

    // Launch the chooser to let the user pick the app
    startActivity(Intent.createChooser(intent, "Share via"))
}
