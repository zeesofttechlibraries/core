package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.view.ViewGroup
import android.widget.ImageView
import jp.wasabeef.blurry.Blurry

/**
 * BaseBlurry:
 * Utility object for applying and removing blur effect on an Activity's root view.
 *
 * This is used by CustomLoading to blur the underlying Activity when showing a dialog.
 */
object BaseBlurry {

    /**
     * addBlur():
     * Captures the current UI of the Activity, applies a blur effect, and sets it on an overlay ImageView.
     *
     * @param activityRoot The root ViewGroup of the Activity (usually android.R.id.content).
     *                     This is the view that will be captured and blurred.
     * @param activity     The Activity context required by Blurry library.
     * @param blurOverlay  ImageView that will hold the blurred bitmap.
     */
    fun addBlur(
        activityRoot: ViewGroup,
        activity: Activity,
        blurOverlay: ImageView?,
    ) {
        // Use the Blurry library to blur the current activityRoot
        Blurry.with(activity)
            .radius(15)   // Amount of blur (higher = blurrier)
            .sampling(2)  // Downscale factor to speed up blur (higher = faster but less detailed)
            .async()      // Do the blur asynchronously to avoid blocking the UI thread
            .capture(activityRoot) // Capture bitmap of the activityRoot
            .getAsync { bitmap ->  // Callback when blur is ready
                // Set the blurred bitmap to the overlay ImageView
                blurOverlay?.setImageBitmap(bitmap)

                // Animate the overlay from transparent to visible for a smooth fade-in
                blurOverlay?.animate()
                    ?.alpha(1f)           // final alpha (fully visible)
                    ?.setDuration(250)    // animation duration in milliseconds
                    ?.start()
            }
    }

    /**
     * removeBlur():
     * Safely removes the blur overlay from the Activity root and resets the reference.
     *
     * @param activityRoot The root view from which the overlay will be removed.
     * @param blurOverlay  The ImageView currently showing the blurred bitmap.
     * @param setBlurOverlayNull Lambda to set the blurOverlay variable in the caller to null.
     *                           This ensures references are cleared to avoid memory leaks.
     */
    fun removeBlur(
        activityRoot: ViewGroup,
        blurOverlay: ImageView?,
        setBlurOverlayNull: () -> Unit
    ) {
        // Animate the overlay fading out
        blurOverlay?.animate()
            ?.alpha(0f)               // fade out to fully transparent
            ?.setDuration(350)        // fade-out duration
            ?.withEndAction {
                // Once the fade-out is complete, remove the ImageView from the root
                activityRoot.removeView(blurOverlay)
            }

        // Reset the overlay reference to null in the calling code
        setBlurOverlayNull.invoke()
    }
}
