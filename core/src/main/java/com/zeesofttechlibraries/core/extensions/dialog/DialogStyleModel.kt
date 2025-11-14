package com.spellchecker.core.dialog

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

/**
 * A model that defines the dynamic visual styling for the custom dialog.
 *
 * This allows full control over background, icons, and button appearance
 * without modifying XML layouts.
 *
 * Example use:
 * ```
 * val style = DialogStyleModel(
 *     backgroundDrawable = R.drawable.bg_dialog_blue,
 *     iconDrawable = R.drawable.ic_info,
 *     iconTintColor = context.getColor(R.color.white),
 *     positiveButtonBackground = R.drawable.bg_positive_button,
 *     negativeButtonBackground = R.drawable.bg_negative_button
 * )
 * ```
 */
data class DialogStyleModel(

    /** Optional background drawable for the entire dialog layout. */
    @DrawableRes val backgroundDrawable: Int? = null,

    /** Optional icon displayed at the top of the dialog (e.g., info, warning). */
    @DrawableRes val iconDrawable: Int? = null,

    /** Optional tint color applied to the icon if set. */
    @ColorInt val iconTintColor: Int? = null,

    /** Optional drawable background for the positive (confirm/ok) button. */
    @DrawableRes val positiveButtonBackground: Int? = null,

    /** Optional drawable background for the negative (cancel) button. */
    @DrawableRes val negativeButtonBackground: Int? = null
)
