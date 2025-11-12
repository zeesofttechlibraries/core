package com.zeesofttechlibraries.core.extensions

import android.util.Patterns

/**
 * Extension function to validate if a string is a valid phone number.
 *
 * Usage:
 *  val isValid = "03001234567".validatePhoneNumber()
 *
 * @return True if the string is a valid phone number, false otherwise.
 */
fun String.isValidPhoneNumber(): Boolean {
    return this.isNotEmpty() && Patterns.PHONE.matcher(this).matches()
}
