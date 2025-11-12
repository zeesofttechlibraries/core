package com.zeesofttechlibraries.core.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Utility functions for common Date & Time operations:
 * - Formatting
 * - Parsing
 * - Relative time ("x minutes ago")
 */

/**
 * Formats a Date object to a given pattern.
 *
 * @param pattern Format pattern (default = "yyyy-MM-dd HH:mm:ss")
 * @return Formatted date string.
 *
 * Example:
 *  val dateString = Date().formatTo("dd MMM yyyy")  // "12 Nov 2025"
 */
fun Date.formatTo(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(this)
}

/**
 * Parses a date string to a Date object.
 *
 * @param pattern The date pattern to parse.
 * @return Date object or null if parsing fails.
 *
 * Example:
 *  val date = "2025-11-12 14:30:00".toDate("yyyy-MM-dd HH:mm:ss")
 */
fun String.toDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    return try {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.parse(this)
    } catch (e: Exception) {
        null
    }
}

/**
 * Converts a Date into a "time ago" style string.
 *
 * Example:
 *  val text = Date().minusMinutes(5).toRelativeTime() // "5 minutes ago"
 */
fun Date.toRelativeTime(): String {
    val now = Date()
    val diffInMillis = now.time - this.time

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
    val days = TimeUnit.MILLISECONDS.toDays(diffInMillis)

    return when {
        diffInMillis < 60_000 -> "just now"
        minutes < 60 -> "$minutes minute${if (minutes != 1L) "s" else ""} ago"
        hours < 24 -> "$hours hour${if (hours != 1L) "s" else ""} ago"
        days == 1L -> "yesterday"
        days < 7 -> "$days day${if (days != 1L) "s" else ""} ago"
        else -> this.formatTo("dd MMM yyyy")
    }
}

/**
 * Helper to subtract minutes easily for testing or usage.
 */
fun Date.minusMinutes(minutes: Int): Date {
    val cal = Calendar.getInstance().apply {
        time = this@minusMinutes
        add(Calendar.MINUTE, -minutes)
    }
    return cal.time
}
