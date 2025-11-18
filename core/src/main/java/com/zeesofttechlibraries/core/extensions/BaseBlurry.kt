package com.zeesofttechlibraries.core.extensions

import android.app.Activity
import android.view.ViewGroup
import android.widget.ImageView
import jp.wasabeef.blurry.Blurry

object BaseBlurry {

    // A unique ID for each blur request
    private var blurRequestId = 0

    fun addBlur(
        activityRoot: ViewGroup,
        activity: Activity,
        blurOverlay: ImageView?
    ) {
        // Create new request id
        val requestId = ++blurRequestId

        // Start async blur
        Blurry.with(activity)
            .radius(15)
            .sampling(2)
            .async()
            .capture(activityRoot)
            .getAsync { bitmap ->

                // If another blur started after this one → ignore this callback
                if (requestId != blurRequestId) return@getAsync

                blurOverlay?.setImageBitmap(bitmap)

                blurOverlay?.animate()
                    ?.alpha(1f)
                    ?.setDuration(250)
                    ?.start()
            }
    }

    fun removeBlur(
        activityRoot: ViewGroup,
        blurOverlay: ImageView?,
        setBlurOverlayNull: () -> Unit
    ) {
        // Invalidate previous blur requests
        blurRequestId++

        if (blurOverlay == null) {
            setBlurOverlayNull()
            return
        }

        blurOverlay.animate()
            .alpha(0f)
            .setDuration(350)
            .withEndAction {
                activityRoot.removeView(blurOverlay)
                setBlurOverlayNull()
            }
            .start()
    }
}
