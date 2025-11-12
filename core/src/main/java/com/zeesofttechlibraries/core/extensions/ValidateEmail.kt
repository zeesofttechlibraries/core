package com.zeesofttechlibraries.core.extensions

import android.util.Patterns

/**
 * Extension function to validate if a string is a proper email address.
 *
 * Usage:
 *  val isValid = "test@example.com".validateEmail()
 *
 * @return True if the string is a valid email, false otherwise.
 */
fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
