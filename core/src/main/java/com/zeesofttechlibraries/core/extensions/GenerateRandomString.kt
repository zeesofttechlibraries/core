package com.zeesofttechlibraries.core.extensions

import kotlin.random.Random

/**
 * Generates a random alphanumeric string.
 *
 * Usage:
 *  val randomId = generateRandomString(10)
 *
 * @param length The desired length of the random string (default = 8)
 * @return A randomly generated string containing [A–Z, a–z, 0–9]
 */
fun generateRandomString(length: Int = 8): String {
    val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length).map { allowedChars.random() }.joinToString("")
}
