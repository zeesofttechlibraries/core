package com.zeesofttechlibraries.core.extensions

import androidx.annotation.DrawableRes
import com.zeesofttechlibraries.core.R

/**
 * Centralized access to all drawable icons in the core module.
 *
 * Usage:
 * val iconId = GetIcons.getIcons(GetIcons.Icon.BACK_IC)
 * imageView.setImageResource(iconId)
 *
 * Benefits:
 * 1. Keep drawable folder clean in feature modules.
 * 2. Reuse icons across the app without duplicating resources.
 * 3. IDE autocomplete and hover hints show all available icon names.
 */
object GetIcons {

    /** All available icons as constants for IDE autocomplete and hover hints */
    sealed interface Icon {
        companion object {
            const val ALERT_DIALOG_ICON_BG = "alert_dialog_icon_bg"
            const val BACK_IC = "back_ic"
            const val BTN_BG = "btn_bg"
            const val BTN_RED_BG = "btn_red_bg"
            const val CHECK_IC = "check_ic"
            const val CLEAR = "clear"
            const val CROSS = "cross"
            const val CUSTOM_TOAST_BG = "custom_toast_bg"
            const val DEFAULT_TOAST_ICON = "default_toast_icon"
            const val DOTS = "dots"
            const val IC_CLEAR = "ic_clear"
            const val IC_DELETE_WHITE = "ic_delete_white"
            const val IC_DELETE1 = "ic_delete1"
            const val IC_DICTIONARY = "ic_dictionary"
            const val IC_DIRECTIONS = "ic_directions"
            const val IC_DOWN = "ic_down"
            const val IC_DOWNARROW = "ic_downarrow"
            const val IC_DROPDOWN_UP = "ic_dropdown_up"
            const val IC_DROPDOWN = "ic_dropdown"
            const val IC_FLASH_OFF = "ic_flash_off"
            const val IC_FLASH_ON = "ic_flash_on"
            const val IC_FORWARD_ARROW = "ic_forward_arrow"
            const val IC_GALLERY = "ic_gallery"
            const val IC_GRAMMARCHECK = "ic_grammarcheck"
            const val IC_HELP_OUTLINE = "ic_help_outline"
            const val IC_HISTORY = "ic_history"
            const val IC_HOME = "ic_home"
            const val IC_LIGHT_THEME = "ic_light_theme"
            const val IC_MENU = "ic_menu"
            const val IC_MIC = "ic_mic"
            const val IC_NEXT = "ic_next"
            const val IC_NOT_FOUND = "ic_not_found"
            const val IC_OTHER = "ic_other"
            const val IC_PASTE = "ic_paste"
            const val IC_REFRESH = "ic_refresh"
            const val IC_REMOTE = "ic_remote"
            const val IC_REMOVE_ADS = "ic_remove_ads"
            const val IC_RESTORE = "ic_restore"
            const val IC_SEARCH_ = "ic_search_"
            const val IC_SEARCH = "ic_search"
            const val IC_SELECTED = "ic_selected"
            const val IC_SETTINGS_IC = "ic_settings_ic"
            const val IC_SETTINGS_WHITE = "ic_settings_white"
            const val IC_SETTINGS = "ic_settings"
            const val IC_SHARE_IMAGE = "ic_share_image"
            const val IC_SHARE_OUTLINE = "ic_share_outline"
            const val IC_SHARE_PDF_SOL = "ic_share_pdf_sol"
            const val IC_SHARE_SOLUTION = "ic_share_solution"
            const val IC_SHARE_WHITE = "ic_share_white"
            const val IC_SHARING = "ic_sharing"
            const val IC_SHOPPING = "ic_shopping"
            const val IC_SPEECH_TO_TEXT = "ic_speech_to_text"
            const val IC_SPEECH = "ic_speech"
            const val IC_TEXT_TO_SPEECH = "ic_text_to_speech"
            const val IC_TRANSLATION = "ic_translation"
            const val IC_TRANSPORTATION = "ic_transportation"
            const val IC_UNFAVOURITE = "ic_unfavourite"
            const val IC_UNLIMITED = "ic_unlimited"
            const val IC_VIEW_DETAILS = "ic_view_details"
            const val IC_VOICE_TRANSLATOR = "ic_voice_translator"
            const val IC_VOLUME_OFF = "ic_volume_off"
            const val ICON_CALCULATOR = "icon_calculator"
            const val ICON_FEEDBACK = "icon_feedback"
            const val ICON_FORWARD = "icon_forward"
            const val ICON_MOON_THEME = "icon_moon_theme"
            const val ICON_STAR = "icon_star"
            const val LOTTIE_ANIMATION_BG = "lottie_animation_bg"
            const val MIX_SHAPE = "mix_shape"
            const val WARNING_BG = "warning_bg"
            const val WARNING = "warning"
        }
    }

    /**
     * Get the drawable resource ID by icon name.
     *
     * @param iconName The name of the icon (use GetIcons.Icon constants for autocomplete).
     * @return Drawable resource ID (e.g., R.drawable.ic_search) or 0 if not found.
     */
    @DrawableRes
    fun getIcons(iconName: String): Int {
        return when(iconName) {
            Icon.ALERT_DIALOG_ICON_BG -> R.drawable.alert_dialog_icon_bg
            Icon.BACK_IC -> R.drawable.back_ic
            Icon.BTN_BG -> R.drawable.btn_bg
            Icon.BTN_RED_BG -> R.drawable.btn_red_bg
            Icon.CHECK_IC -> R.drawable.check_ic
            Icon.CLEAR -> R.drawable.clear
            Icon.CROSS -> R.drawable.cross
            Icon.CUSTOM_TOAST_BG -> R.drawable.custom_toast_bg
            Icon.DEFAULT_TOAST_ICON -> R.drawable.default_toast_icon
            Icon.DOTS -> R.drawable.dots
            Icon.IC_CLEAR -> R.drawable.ic_clear
            Icon.IC_DELETE_WHITE -> R.drawable.ic_delete_white
            Icon.IC_DELETE1 -> R.drawable.ic_delete1
            Icon.IC_DICTIONARY -> R.drawable.ic_dictionary
            Icon.IC_DIRECTIONS -> R.drawable.ic_directions
            Icon.IC_DOWN -> R.drawable.ic_down
            Icon.IC_DOWNARROW -> R.drawable.ic_downarrow
            Icon.IC_DROPDOWN_UP -> R.drawable.ic_dropdown_up
            Icon.IC_DROPDOWN -> R.drawable.ic_dropdown
            Icon.IC_FLASH_OFF -> R.drawable.ic_flash_off
            Icon.IC_FLASH_ON -> R.drawable.ic_flash_on
            Icon.IC_FORWARD_ARROW -> R.drawable.ic_forward_arrow
            Icon.IC_GALLERY -> R.drawable.ic_gallery
            Icon.IC_GRAMMARCHECK -> R.drawable.ic_grammarcheck
            Icon.IC_HELP_OUTLINE -> R.drawable.ic_help_outline
            Icon.IC_HISTORY -> R.drawable.ic_history
            Icon.IC_HOME -> R.drawable.ic_home
            Icon.IC_LIGHT_THEME -> R.drawable.ic_light_theme
            Icon.IC_MENU -> R.drawable.ic_menu
            Icon.IC_MIC -> R.drawable.ic_mic
            Icon.IC_NEXT -> R.drawable.ic_next
            Icon.IC_NOT_FOUND -> R.drawable.ic_not_found
            Icon.IC_OTHER -> R.drawable.ic_other
            Icon.IC_PASTE -> R.drawable.ic_paste
            Icon.IC_REFRESH -> R.drawable.ic_refresh
            Icon.IC_REMOTE -> R.drawable.ic_remote
            Icon.IC_REMOVE_ADS -> R.drawable.ic_remove_ads
            Icon.IC_RESTORE -> R.drawable.ic_restore
            Icon.IC_SEARCH_ -> R.drawable.ic_search_
            Icon.IC_SEARCH -> R.drawable.ic_search
            Icon.IC_SELECTED -> R.drawable.ic_selected
            Icon.IC_SETTINGS_IC -> R.drawable.ic_settings_ic
            Icon.IC_SETTINGS_WHITE -> R.drawable.ic_settings_white
            Icon.IC_SETTINGS -> R.drawable.ic_settings
            Icon.IC_SHARE_IMAGE -> R.drawable.ic_share_image
            Icon.IC_SHARE_OUTLINE -> R.drawable.ic_share_outline
            Icon.IC_SHARE_PDF_SOL -> R.drawable.ic_share_pdf_sol
            Icon.IC_SHARE_SOLUTION -> R.drawable.ic_share_solution
            Icon.IC_SHARE_WHITE -> R.drawable.ic_share_white
            Icon.IC_SHARING -> R.drawable.ic_sharing
            Icon.IC_SHOPPING -> R.drawable.ic_shopping
            Icon.IC_SPEECH_TO_TEXT -> R.drawable.ic_speech_to_text
            Icon.IC_SPEECH -> R.drawable.ic_speech
            Icon.IC_TEXT_TO_SPEECH -> R.drawable.ic_text_to_speech
            Icon.IC_TRANSLATION -> R.drawable.ic_translation
            Icon.IC_TRANSPORTATION -> R.drawable.ic_transportation
            Icon.IC_UNFAVOURITE -> R.drawable.ic_unfavourite
            Icon.IC_UNLIMITED -> R.drawable.ic_unlimited
            Icon.IC_VIEW_DETAILS -> R.drawable.ic_view_details
            Icon.IC_VOICE_TRANSLATOR -> R.drawable.ic_voice_translator
            Icon.IC_VOLUME_OFF -> R.drawable.ic_volume_off
            Icon.ICON_CALCULATOR -> R.drawable.icon_calculator
            Icon.ICON_FEEDBACK -> R.drawable.icon_feedback
            Icon.ICON_FORWARD -> R.drawable.icon_forward
            Icon.ICON_MOON_THEME -> R.drawable.icon_moon_theme
            Icon.ICON_STAR -> R.drawable.icon_star
            Icon.LOTTIE_ANIMATION_BG -> R.drawable.lottie_animation_bg
            Icon.MIX_SHAPE -> R.drawable.mix_shape
            Icon.WARNING_BG -> R.drawable.warning_bg
            Icon.WARNING -> R.drawable.warning
            else -> 0
        }
    }
}
