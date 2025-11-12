package com.zeesofttechlibraries.core.extensions

import android.view.View

/**
 * Extension functions to easily change the visibility of a View.
 * These help reduce repetitive code when showing or hiding views.
 */

/**
 * Makes the view visible.
 */
fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

/**
 * Hides the view and removes it from the layout.
 */
fun View.makeGone() {
    this.visibility = View.GONE
}

/**
 * Optionally, you can add a convenience function for invisible state:
 * Keeps the space but hides the view.
 */
fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}
